# Minesweeper
Authors: Carissa Bolante, Frances McConnell, Redding Sauter

Project description:
A game of Minesweeper.
Java 17 installation required.
The main class is in the Minesweeper file.

Most of our game features were based on the Google version of Minesweeper. 
We used the following websites for helper methods:
https://easings.net/#easeOutElastic (found in FlyIn.java)
https://easings.net/#easeOutBack (found in SizeIn.java)
We used this link for our red flag image:
https://www.iconfinder.com/icons/2593657/banner_flag_flaglets_red_flag_icon
To learn more about the rules and history of the game:
https://en.wikipedia.org/wiki/Minesweeper_(video_game)

Known issues:
- If the user enters an even number for grid size in the custom game mode the coloration of the game board will be striped instead of checkered.
- If the user is clicking through the tiles too quickly, some of the tiles clicked do not always update immediately.

Societal impact:
- Our game cannot be played by blind people. It is completely silent and does not have sound effects that indicate the status of the game or where to click. Colorblind people, however, can still play the game. The numbers on the clear tiles indicate which tiles are not bombs and the flag image is there for them to see which tiles they marked as bombs. The moment they click a bomb, the game will indicate they lost. The game may be inaccessible to users who don’t speak English as the instructions and game modes are all in English.
- Another societal impact that could be made with our code is if someone replaces our red flag image with other harmful images since we did not create the flag using multiple GraphicsObject objects ourselves. An example could be if the red flag was replaced with a person’s face to make a political statement.

How to Play:
1. Run the main method in the Minesweeper.java file to start the game. Select a game mode:
- Easy—11x11 grid with 15 bombs
- Medium—15x15 grid with 30 bombs
- Hard—21x21 grid with 99 bombs
- Custom—Use the terminal to choose your own grid size and number of bombs 

2. Click anywhere on the grid to reveal empty tiles. The goal of the game is to reveal all tiles without bombs. Tiles that are brown with no number on them border no bombs. Other tiles have numbers that describe the number of bombs present in the 8 tiles adjacent to them. 

3. Use SHIFT+CLICK to place a red flag which can be used to mark where a bomb may be.

4. CLICK a tile to flip it and check the status. If a tile with a bomb is clicked the game ends and you lose.

5. If you reveal all the tiles without bombs, you win.

6. Once the game ends, there are two options:
- Play again: Go back to the main screen to choose a mode and start over.
- Exit: Closes the window.
