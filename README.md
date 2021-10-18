# /discord

#### A basic mod which adheres to the "Do one thing well" philosophy.

## Setup

1. Download the mod from the Releases section in the sidebar, then put it in your server's `mods` folder.
2. Create `server/config/slashdiscord.json` and insert into it your config. The config should be valid json and look somewhat like this:
```json
{
  "discord_link": "https://discord.gg/minecraft",
  "message": "Click here to join our discord server!",
  "color": "GOLD",
  "names": ["discord"]
}
```
`discord_link` should be a permanent invite link to your server <br>
`message` should be the message you want to appear when the command is run <br>
`color` should be a [minecraft color code](https://minecraft.fandom.com/wiki/Formatting_codes#Color_codes) <br>
`names` should be a list of names you want the command to have
3. There is no third step

(This mod does require the [Fabric API](https://modrinth.com/mod/fabric-api), but you probably have that already)

## License

This mod is available under the MIT licence.
