# /discord

#### A basic mod which adheres to the "Do one thing well" philosophy.

## Setup

1. Download the mod from the [modrinth](https://modrinth.com/mod/slashdiscord), then put it in your server's `mods` folder.
2. Create `server/config/slashdiscord.json` and insert into it your config. The config should be valid json and look
   somewhat like this:

```json
{
  "commands": [
    {
      "target": "https://github.com/randomairborne/slashdiscord/blob/master/README.md",
      "message": "Click to configure slashdiscord!",
      "color": "AQUA",
      "names": [
        "discord",
        "setup"
      ],
      "on_click": "OPEN_URL"
    },
    {
      "target": "-7115307996784423713",
      "message": "Click to copy world seed!",
      "color": "GREEN",
      "hover": {
        "message": "Click to copy",
        "color": "GOLD"
      },
      "names": [
        "worldseed"
      ],
      "on_click": "COPY_TO_CLIPBOARD"
    }
  ]
}

```
3. There is no third step

## Getting Help
Please ask in my [Discord](https://valk.sh/discord) for support and help.
## License

This mod is available under the MIT licence.
