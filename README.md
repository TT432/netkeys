ENGLISH:

# NetKeys —— Command Keybinding

## How to Use:

In the server's configuration files, you can find the `netkeys-server.toml` file.

You will see a file that looks like this:

```toml
#Set of keys.
[keyMap]
	"CONTROL + C" = "say 1"
```

The `CONTROL + C` part specifies the shortcut keys. The first part (`CONTROL`) is the modifier, which includes the following options:

* `SHIFT`
* `CONTROL`
* `ALT`
* `SUPER`
* `CAPS_LOCK`
* `NUM_LOCK`

The second part (`C`) is the key portion, and generally any key can be used.

The `say 1` part specifies the command to be executed on behalf of the Server, so you need to be cautious in choosing the command.

CHINESE:

# NetKeys —— 指令绑定快捷键

## 使用方法：

在服务端的配置文件可以看到 `netkeys-server.toml` 文件。

你能看到类似如下的文件：

```toml
#Set of keys.
[keyMap]
	"CONTROL + C" = "say 1"
```

`CONTROL + C` 部分指定快捷键，前部分 (`CONTROL`) 是修饰符，包含以下可选项：
- `SHIFT`
- `CONTROL`
- `ALT`
- `SUPER`
- `CAPS_LOCK`
- `NUM_LOCK`

后半部分 (`C`) 是键部分，一般按键都可以使用。

`say 1` 部分指定指令，以 Server 的身份执行，因此需要谨慎选择指令。