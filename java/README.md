Final project for CIS112 Data Structures and Algorithms

Data structures used:
Since Euchre is a turned-based card game, and the play always follows the same order, I mostly used linked list-based ADT implmentations.

LinkedQueue (in Table class): I stored the list of Players in a linked queue so that the dealer is always at rear. When the dealer changes, a simple dequeue/enqueue rotates the new dealer to the end, and the play resumes from the front of the queue. A circular linked queue would have been ideal, but I didn't have the time to implement it and account for all the 'special cases' that ADT entails.

LinkedStack (in Deck class): When dealing cards, you only need to access the top card, so a stack is ideal. To shuffle, my implementation (recursively!) transfers the cards into an ArrayList, then uses the built-in Collections.shuffle to mix them up. Since shuffling occurs infrequently, the one-time cost is minimal. 

DoubleLinkedDeque (in Trick class): , To help deal with the dynamic value changes (Since the rank-values change depending on the trump suit), I placed the cards played into the trick in a Double-Linked Queue. Each new card played is evaluated as higher or lower than the current highest-valued card. If the new card is higher, it's placed at the front of the deque; if lower, it's placed at the back of the deque. After all the cards have been played the front card is winner.

SortedABList (in Player class): To keep player's hands arranged in a sensible order, I used a SortedABList with a custom Comparator (added to the Card class) to keep cards grouped by suit and ordered by rank within each suit-group.

LinkedStack (in Player class): The object of the game is for each team to take tricks, so I needed a simple way to keep track of the number of tricks a player takes during a round. The LinkedStack is the simplest data structure with a size instance variable, so that was the perfect candidate. I wanted to store the actual tricks/cards rather than use a simple accumulator in case I want to modify the program to display the cards played later on. 


Other notable things:
    Used enum data types for Card rank/suit/color, as well as the Player team positions.
    Since the value of each card is not inherent, added method to set the value dynamically based on the trump suit called.
    I tried to follow the Model-View-Controller paradigm, and so have arranged the classes accordingly. I spent some time trying to make a GUI with the JavaFX library, and having the prompts grouped under the View package should make that easier to implement later on.