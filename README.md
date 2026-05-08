<h1>CAR RACE OOP PROJECT</h1>

[car_race.pdf](https://github.com/user-attachments/files/27531775/car_race.pdf)

 
<h1>RACE TOURNAMENT SIMULATION </h1>

<h2>1.  Objective </h2>
         In this assignment, you are expected to design and implement a race tournament system using linked 
list data structures. The system should manage cars, tracks, and race results efficiently.  
         You are expected to store and process cars and tracks using a Single Linked List (SLL), manage 
races using a Double Linked List (DLL) to simulate tournament progression. The goal is to demonstrate 
correct use of data structures, dynamic data handling, and logical flow of a multi-stage tournament system. </br>

<h2>2.  Data Files </h2>
2.1  cars.txt 
ID Name Performance Score Type 
101 Thunderbolt 250 Electric 
102 IronClad 240 Heavy 
103 BlazeRunner 245 Fire 
104 AquaDrift 240 Water 
105 TerraCrusher 235 Earth 
106 WindPhantom 255 Air 
2.2  tracks.txt 
ID Name Type Boost 
201 Neon Circuit Electric 20 
202 Volcano Run Fire 25 
203 Ocean Drift Water 15 
204 Mountain Pass Earth 10 
205 Sky Highway Air 30 
206 Industrial Zone Heavy 20 
 
 
<h2>3. Game Elements   </h2>
3.1  Game Players 
The game features two types of participants: the Human Player and the Computer. When a Player races against 
the Computer, the match takes place on the track selected by the Player, as they have track-selection priority. 
However, in Computer vs. Computer matches, the track is assigned randomly. 
3.2  Cars 
All 6 cars should be stored in a Single Linked List (SLL). The list must be kept sorted in increasing order according 
to performance score. This means the car with the lowest performance is at the head, and the highest is at the 
end. The list should be traversed to display cars and select a car by ID. 
3.3 Type Matchup Table 
When a car of type “Type A” races against a Type B car on any track, it receives the specified bonus as follows: 
Type A   Type B   Matchup Bonus 
Electric Water +15 
Water Fire +15 
Fire Earth +15 
Earth Electric +15 
Air Earth +10 
Heavy Air +10 
 
Example A  —  Thunderbolt (Electric) vs AquaDrift (Water): 
   Electric > Water  →  Thunderbolt gains +15 matchup bonus. 
   AquaDrift gains 0 (no reverse advantage in this matchup). 
 
Example B  —  BlazeRunner (Fire) vs IronClad (Heavy): 
   Neither Fire→Heavy nor Heavy→Fire appears in the table. 
   Both cars get no matchup bonus. 
 
Example C  —  WindPhantom (Air) vs TerraCrusher (Earth): 
   Air > Earth  →  WindPhantom gains +10 matchup bonus. 
   TerraCrusher gains 0. 
 
When we examine the matchup table, we see a rule stating that the Electric type is stronger than the Water type. 
This means that when an Electric car races against a Water car, the Electric car receives a bonus. In this example, 
Thunderbolt is an Electric-type car and AquaDrift is a Water-type car, so Thunderbolt receives a +15 bonus. 
However, there is no rule indicating that Water is stronger than Electric. Therefore, AquaDrift does not receive 
any bonus. This demonstrates that matchup bonuses apply only in one direction, not both. 
Similarly, the matchup table shows that Air is stronger than Earth. This means that when an Air car races against 
an Earth car, the Air car receives a bonus. In this case, WindPhantom is an Air-type car and TerraCrusher is an 
Earth-type car, so WindPhantom receives a +10 bonus. Since there is no rule stating that Earth is stronger than 
Air, TerraCrusher does not receive any bonus. This example, like the previous one, illustrates that only the type 
specified in the table gains the advantage. 
3.4 Tracks  
Gameplay takes place across several different racing tracks, which are listed in tracks.txt. Each track has a total 
length of 50 units. 
All 6 tracks are stored in a Single Linked List (SLL). The tracks do not need to be sorted in any order. After each 
race, the used track is removed from the list. The list should be updated accordingly and print the remaining tracks 
after removal. 
3.5 Movement and Interaction Logic 
The race should be performed on a Double Linked List (DLL). During the simulation, cars do not move at a constant 
speed. Instead, the following rules apply: 
1. In each iteration, every car advances a random distance between 1 and 3 units (inclusive).  
2. A car loses 5 performance points from their current score for each unit it moves. Additionally, any 
teleportation or reset operation always reduces the performance score by 5 points, regardless of the 
number of units traveled. 
3.6 Special Operations  
The teleport units and the trap unit are randomly distributed along the track according to the following rules: 
Teleportation Units: Exactly 10 units are designated for teleportation. When a car lands on one of these units, it 
is moved forward or backward by a randomly generated distance between 1 and 5 units (inclusive). 
Reset Trap: Only one unit on the track is designated for the “Reset Operation,” which immediately sends a car 
back to the starting point, regardless of its current position. 
Normal Units: The remaining units have no special functionality and serve as regular track segments. 
Example — Movement with Teleport (Using 50-unit track) 
Iteration 1 — Thunderbolt starts at Unit 1: 
Moves +2 steps → Unit 3 (+2 steps assigned as random between 1 and 3) 
Unit 3 has a teleportation effect = +3 → teleport to Unit 6 (3 + 3 = 6) 
There is no effect in Unit 6 → stop here 
Thunderbolt ends Iteration 1 at Unit 6 
Iteration 2 — Thunderbolt moves from Unit 6: 
Moves +1 step → Unit 7 (+1 step assigned as random between 1 and 3) 
There is no effect in Unit 7 → stop here 
Thunderbolt ends Iteration 2 at Unit 7 
Iteration 3 — Thunderbolt moves from Unit 7: 
Moves +1 step → Unit 8 (+1 step assigned as random between 1 and 3) 
Unit 8 has a teleportation effect = -4 → teleport to Unit 4 (8 - 4 = 4) 
There is no effect in Unit 4 → stop here 
Thunderbolt ends Iteration 3 at Node 4 
3.7 Initial Score Calculation 
Initial Score   =  Performance Score  +  Track Effect  +  Matchup Bonus 
 
Race Track Effect: 
   ▸  If car type = track type:  Track Effect  =  +boost   
   ▸  If car type ≠ track type:  Track Effect  =  no boost 
 
Matchup Bonus: 
   Check MATCHUP table:   
   ▸  if (car type, opponent type) is listed → add bonus 
   ▸  not listed → no bonus  
The initial score of a car is calculated based on three components: performance score, track effect, and matchup 
bonus. First, each car has a base performance score defined in the cars.txt file. Next, the track effect is considered. 
If the car’s type matches the track type, it receives a bonus; otherwise, no bonus is applied. Finally, the matchup 
bonus is determined using the matchup table. If a rule exists between the car’s type and the opponent’s type, the 
car receives the corresponding bonus; if no such rule exists, no bonus is given. The final score is the sum of all 
these components. 
In the following example, WindPhantom is an Air-type car racing on Sky Highway, which is also an Air-type track. 
Therefore, WindPhantom receives a +30 track bonus. By adding this bonus to its base performance score of 255, 
and considering that there is no matchup bonus, the final performance score is calculated as 285. 
Example 1: 
WindPhantom (Air) on Sky Highway (Air), vs BlazeRunner (Fire) 
● Base score = 255  
● Track type = Air, car type = Air → MATCH  
● Track Effect = +30 (boost from track)  
● Matchup = Air vs Fire → not in table → 0  
Initial Score = 255 + 30 + 0 = 285 
In the following example, IronClad is a Heavy-type car, while the track is Air-type. Since the types do not match, 
IronClad does not receive any track bonus, so the track effect is 0. Next, we check the matchup table, which shows 
that Heavy is stronger than Air; therefore, IronClad receives a +10 matchup bonus. 
 
 
 
 
Example 2: 
IronClad (Heavy) on Sky Highway (Air track), vs WindPhantom (Air) 
● Base = 240  
● Track type = Air, car type = Heavy → mismatch  
● Track Effect = 0 (no boost because types are different)  
● Matchup = Heavy vs Air → in table → +10  
Final = 240 + 0 + 10 = 250 
3.8 End of Race Condition 
The race ends when one of the players reaches the last unit (Unit 50) or when one of the players’ performance 
points are depleted. In the event of a tie, the winner is the player who completed the race in fewer iterations. 
3.9  Race Log 
Once the final champion is determined, the system must retrieve all records from the beginning to the end of the 
tournament and display the complete history of results in the correct order. The race log should be kept inside a 
Single Linked List (SLL). 
Sample Content of the Race Log 
After the completion of the first race, a summary is created containing the following data points: 
● Race Identity: Round 1a. 
● Location: Sky Highway. 
● Competitor 1: Thunderbolt, finishing with 186 performance points in 8 iterations. 
● Competitor 2: IronClad, finishing with 216 performance points in 5 iterations. 
● Result: IronClad is declared the winner. 
This structured history allows the program to provide a clear look back at the performance calculations and 
iteration counts that led to the final victory. 
<h2>4.  The Main Flow of the Tournament  </h2>
In the first race, the player selects a track and a car. Then the computer chooses a car that has an advantage over 
the selected car. A race is held on the chosen track, and the winner is added to the log list. After that, the computer 
randomly selects a track from the remaining unselected tracks and races against another computer opponent. 
The winner is again added to the log list. Finally, the winners taken from the log list compete against each other 
on a different track using selected cars, and the winner of this game becomes the overall game winner. 
 
Step 1 — Load Data 
 Load all 6 cars into the system. 
 Load all 6 race tracks into the system.
Step 2 — Player Chooses a Track 
 The player selects one race track. 
Step 3 — Player Chooses Car 
 The player selects a car among the 6 available cars. 
 (Hint: The player should choose the car carefully according to the selected track)
Step 4 — Computer Chooses Opponent 
 After the player selects a car, the computer automatically chooses the best car against the player. 
 The computer decision must consider: 
● the selected track 
● the player’s car type 
 
Step 5  — Perform the Race 
The first race named as Race 1a (Player vs computer) is performed.
Step 6 — Assign Remaining Cars and a Track for Another Race 
Among the remaining 4 cars (after Player and Computer selected their cars): 
● 2 cars are randomly selected for Race 1b (computer vs computer) and the game is performed on a 
available and randomly selected track. 
● The other 2 cars become inactive 
Step 7 — Create Log List  
● The Game History of Race 1a: Player vs computer 
● The Game History of Race 1b: Computer vs computer 
● The Game History of Final Race: Winner of Race 1a vs Winner of Race 1b 
 
 
Example scenario: 
1)[Race 1a | SkyHighway | Thunderbolt:186 vs IronClad:216 | WINNER: 
IronClad] 
 ↓ 
2)[Race 1b | SkyHighway | BlazeRunner:195 vs WindPhantom:241 | WINNER: 
WindPhantom] 
 ↓ 
3)[Final   | NeonCircuit | IronClad:195 vs WindPhantom:214 | WINNER: 
WindPhantom] 
 
Race Round Participants Track Track Rule 
1a Round 1 Player vs Computer_1 Track A (chosen by Player) Track A removed after Round 1 
1b Round 1 Computer _2 +  Computer _3 Track B (chosen by  Computer ) Track B removed after Round 1 
Final Final Winner 1a + Winner 1b Track C ( Selected by the player if 
the player win Round 1; otherwise, it 
is selected by the computer ) 
Track C removed after Final Race 
/br
<h2>5.  Sample Console Outputs </h2>
Expected console output format for the full tournament is given below. Both cars race simultaneously on the 
same track. The race log SLL is printed at the end by showing the history of all three races in order. 
<img width="611" height="498" alt="image" src="https://github.com/user-attachments/assets/1c889d0a-305f-4e68-a4fc-7e6d88808d66" />
<img width="622" height="547" alt="image" src="https://github.com/user-attachments/assets/0d77f29e-a24f-4451-a70c-0b7b0fb48898" />
<img width="717" height="578" alt="image" src="https://github.com/user-attachments/assets/9caa3c99-f0ad-4e5b-9792-1d9f6bc2c411" />
<img width="720" height="557" alt="image" src="https://github.com/user-attachments/assets/73ae9f22-1cae-49d2-b3f3-10a67789b34b" />


Sample Output for Race Log 
<img width="517" height="800" alt="image" src="https://github.com/user-attachments/assets/d522d525-c5be-4ad3-9277-3ff56bae58d6" />



<h1><u></u>*NOT FOLLOWING NOTES OR RULES WILL BE SEVERELY PENALIZED*</u> </h1>
<h2>Notes </h2>
1- In your program, you can use the Single Linked List and Double Linked List data structures as much you want, but you 
must only use them. 
Don’t use other data structures such as an array or arraylist or list or any other data structre. 
Don’t use STRING data type in the main solution, instead of a stack or queue. 
2- You must strictly follow the sample output. DON’T CHANGE THE SAMPLE OUTPUT FORMAT. 
3- Don’t use the linked list classes embedded in Java. Use the ones given in the lectures. 
4- Don’t use ENIGMA or any other extra library. 
5- Your program must work correctly under all conditions. Try to control all possible errors. 
6- You should use meaningful variable names, appropriate comments, and good prompting messages.                                                  
