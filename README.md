![maven workflow badge](https://github.com/glasergl/Custom-LoL-Leaderboard/actions/workflows/maven.yml/badge.svg)
# Custom LoL Leaderboard
This program visualizes the best players (Master, Grandmaster or Challenger) of [League of Legends](https://www.leagueoflegends.com/en-gb/).
Furthermore, the tool is capable of showing which players will promote to Grandmaster/Challenger or demote to Grandmaster/Master.
Also, the tool shows the current LP which has to be exceeded to reach Grandmaster/Challenger.

Such that the program is able to query the [API](https://developer.riotgames.com/apis) of Riot Games, there has to exist an environment variable `RIOT_API_KEY_CUSTOM_LADDER` with the value of a valid API key which can be generated by creating a Riot account at [https://developer.riotgames.com/](https://developer.riotgames.com/).

## Screenshots of example visualizations
First few Solo/Duo Challenger Players:
<p align="center">
  <img src="Example_View_1.PNG" width="60%">
</p>

Last few Flex Grandmaster Players:
<p align="center">
  <img src="Example_View_2.PNG" width="60%">
</p>

## Notes
- Windows will likely flag the `.exe` as not trustable. Therefore, in order to correctly run it, it needs to have administrative rights.
