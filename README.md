# Neko Authlib Injector

A VERY simple authlib injector, but it works!

It (might) not depends on any version of `Fabric Loader` and `Minecraft`, so the mod did not ask for any dependency in its `fabric.mod.json`

## Config

Config file is located at working directory of Minecraft server, named `neko-authlib-injector.properties`

```properties
originalUrl=https\://sessionserver.mojang.com
url=https\://example.com
```

Replace the `url` with your yggdrasil server url.

`originalUrl` is the url which will be replaced by the mod, no need to modify it by default.