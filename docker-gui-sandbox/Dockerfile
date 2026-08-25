# 最小 GUI 沙箱镜像：只装 X11 测试小工具 + xterm，验证「容器内程序画到宿主桌面」
FROM ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive

# x11-apps: xeyes / xclock / xlogo / xcalc 等经典 X11 测试程序
# xterm:    终端，可验证键盘输入 / 输入法（配合 fcitx）
RUN apt-get update && apt-get install -y --no-install-recommends \
        x11-apps \
        xterm \
        dbus-x11 && \
    rm -rf /var/lib/apt/lists/*

# 默认跑 xeyes：两只盯着鼠标的眼睛，一眼看出 X 转发是否成功
CMD ["xeyes"]
