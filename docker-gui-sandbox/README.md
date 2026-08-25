# docker-gui-sandbox —— 最小可用的 X11 图形沙箱

验证「容器内 GUI 程序直接画到宿主桌面」的最小模块，配套笔记：
`~/workspace/notes/常用工具/容器中间件/容器/docker作为本地应用沙箱实践.org`

## 文件

| 文件        | 作用                                        |
|-------------|---------------------------------------------|
| `Dockerfile`| 极简镜像：ubuntu:24.04 + x11-apps + xterm   |
| `run.sh`    | 一键启动器：自动探测 DISPLAY/Xauthority/Pulse |

## 使用

```bash
cd ~/workspace/demo/docker-gui-sandbox
chmod +x run.sh

./run.sh            # xeyes：两只眼睛跟随鼠标 = 转发成功
./run.sh xclock     # 时钟窗口
./run.sh xterm      # 终端（可测输入法/键盘）
```

## 原理速览

1. **X11 是网络协议（C/S 架构）**：X Server 在宿主机（管理窗口的正是它），
   容器里的程序只是 X Client。
2. **挂 3 样东西即可转发**：
   - `/tmp/.X11-unix:/tmp/.X11-unix` —— X Server 监听 socket
   - `DISPLAY=:0` —— 告诉容器内程序去哪连
   - `~/.Xauthority:/root/.Xauthority:ro` —— X 认证令牌（access control 开启时必需）
3. 容器只隔离文件系统/进程，**不隔离显示** → 窗口由宿主 X Server 合成，
   桌面窗口管理器把它当普通 X 客户端布局，所以"看着像本地程序"。

## 验证是否真的显示在宿主

```bash
# 从宿主侧查窗口树（xeyes 应在其中）
xwininfo -root -tree | grep -i xeyes
```
