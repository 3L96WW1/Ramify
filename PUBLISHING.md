# 发布检查清单

## 当前版本

- 模组：**麻 / Ramify**
- 版本：`1.0.0+1.21.1`
- 文件：`build/libs/ramify-1.0.0+1.21.1.jar`
- 依赖：Minecraft 1.21.1、Fabric Loader ≥ 0.16、Fabric API、Java 21
- 许可证：MPL-2.0

## 发布前确认

- [ ] `./build.sh build` 构建成功
- [ ] 干净 Fabric 1.21.1 实例只装 Fabric API + 本 jar 能启动
- [ ] 新世界能进入
- [ ] 烧烤架能放置、打开、配方按钮可用
- [ ] 25 个配方至少抽查 5 个
- [ ] 无匹配 4 原料能产出迷之炖菜
- [ ] 蔷薇种子能种、骨粉能催熟、村民赠礼有粒子/成就
- [ ] 梭子蟹/龙虾可用刷怪蛋或 `/summon` 生成
- [ ] 服务端能启动

## GitHub Releases

1. 推送代码和 tag：
   ```bash
   git add -A
   git commit -m "Release 1.0.0+1.21.1"
   git tag v1.0.0+1.21.1
   git push origin main
   git push origin v1.0.0+1.21.1
   ```
2. GitHub → Releases → Draft a new release
3. 选择 tag `v1.0.0+1.21.1`
4. 标题：`麻 / Ramify 1.0.0+1.21.1`
5. 说明复制 `RELEASE.md`
6. 上传 `build/libs/ramify-1.0.0+1.21.1.jar`
7. 发布

## Modrinth / CurseForge

- 游戏版本：1.21.1
- 加载器：Fabric
- 环境：客户端 + 服务端
- 依赖：Fabric API
- 分类：Adventure、Food、World Gen、Mobs
- 图标：`src/main/resources/assets/ramify/icon.png`
- 简介：使用 `RELEASE.md` 的「简介」和「主要内容」

## 已知限制

- 远程/亡灵伤害是状态标记 + 力量近似
- 果汁的缓解燥热用抗火近似
- 烧烤架配方硬编码
- 世界生成需要新区块

## 发布后

- 保留 jar 和源码 tag
- 记录 Fabric API 对应版本
- 如果只是给朋友测试，可直接发 jar，不需要上传平台
