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
      "link": "https://discord.gg/minecraft",
      "message": "Click here to join our discord server!",
      "color": "GOLD",
      "names": [
        "discord"
      ]
    },
    {
      "link": "https://example.com",
      "message": "Click here to visit our website!",
      "color": "AQUA",
      "names": [
        "store",
        "website"
      ]
    }
  ]
}

```
`commands` should be a list of JSON objects of the below format

`discord_link` should be a permanent invite link to your server <br>
`message` should be the message you want to appear when the command is run <br>
`color` should be a [minecraft color code](https://minecraft.fandom.com/wiki/Formatting_codes#Color_codes) <br>
`names` should be a list of names you want the command to have

3. There is no third step

## Getting Help
Please ask in my [Discord](https://valk.sh/discord) for support and help.
## License

This mod is available under the MIT licence.
