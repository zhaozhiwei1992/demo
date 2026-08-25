#!/usr/bin/env bash
# docker-gui-sandbox/run.sh —— 最小可用 X11 图形沙箱启动器
#
# 原理：容器内程序是 X11 客户端，把宿主 X Server 的 socket + Xauthority
#       挂进容器并设置 DISPLAY，程序窗口就直接画在宿主桌面上，
#       对桌面（i3 / GNOME / KDE 等）来说它就是个普通 X 客户端。
#
# 用法：
#   ./run.sh                     # 默认 xeyes（两只眼睛，一眼看出转发成功）
#   ./run.sh xclock              # 时钟
#   ./run.sh xterm               # 终端（可测键盘/输入法）
#   ./run.sh gedit /tmp/a.txt    # 任意容器镜像内置的程序 + 参数
#
# 环境变量（可外部覆盖）：
#   IMAGE    沙箱镜像，默认 docker-gui-sandbox（不存在则自动构建）
#   PROG     要运行的程序，默认 xeyes
#   PULSE    挂载 PulseAudio 与否（yes/no），默认 auto 探测

set -euo pipefail

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
IMAGE="${IMAGE:-docker-gui-sandbox}"
PULSE="${PULSE:-auto}"

# ---------- 1. 前置检查 ----------
[[ -n "${DISPLAY:-}" ]] || { echo "错误: DISPLAY 未设置（需要 X11 会话）"; exit 1; }
command -v docker >/dev/null || { echo "错误: 未安装 docker"; exit 1; }

# ---------- 2. 参数 ----------
PROG="${1:-xeyes}"
shift || true
PROG_ARGS=("$@")

# ---------- 3. 构建镜像（首次） ----------
if ! docker image inspect "$IMAGE" >/dev/null 2>&1; then
  echo "==> 构建沙箱镜像 ${IMAGE} ..."
  docker build -t "$IMAGE" "$DIR"
fi

# ---------- 4. 组装 docker run ----------
# stdin 是 TTY 时用 -it（可交互），否则只挂 -i 或去掉（后台/nohup 场景）
TTY_ARGS=()
if [[ -t 0 ]]; then TTY_ARGS=( -it ); else TTY_ARGS=( -i ); fi
ARGS=(
  --rm "${TTY_ARGS[@]}"
  -e "DISPLAY=${DISPLAY}"
  -v /tmp/.X11-unix:/tmp/.X11-unix
)

# X 认证：X Server 开启了 access control 时必须挂 Xauthority（只读）
if [[ -f "${XAUTHORITY:-$HOME/.Xauthority}" ]]; then
  ARGS+=( -v "${XAUTHORITY:-$HOME/.Xauthority}:/root/.Xauthority:ro" )
  echo "==> Xauthority : ${XAUTHORITY:-$HOME/.Xauthority} (ro)"
else
  # 兜底：放行本地 docker 客户端（仅受信环境）
  xhost +local:docker >/dev/null 2>&1 || true
  echo "!! 未找到 Xauthority，已执行 xhost +local:docker（不推荐）"
fi

# 剪贴板 / 共享内存（xterm 复制粘贴更稳）
ARGS+=( --ipc=host )

# PulseAudio（音频；没有则静默禁用）
if [[ "$PULSE" == "auto" && -S "/run/user/$(id -u)/pulse/native" ]] || [[ "$PULSE" == "yes" ]]; then
  PULSE_SOCK="/run/user/$(id -u)/pulse"
  ARGS+=( -v "${PULSE_SOCK}:${PULSE_SOCK}" -e "PULSE_SERVER=unix:${PULSE_SOCK}/native" )
  echo "==> Pulse      : ${PULSE_SOCK} (mounted)"
fi

# 当前目录挂为 /work，方便传文件
ARGS+=( -v "$(pwd):/work" -w /work )

echo "==> 运行 ${PROG} ${PROG_ARGS[*]:-} (DISPLAY=${DISPLAY})"
exec docker run "${ARGS[@]}" "$IMAGE" "$PROG" "${PROG_ARGS[@]}"
