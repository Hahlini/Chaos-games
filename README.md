# Chaos-games

### What is a Chaos game?

A chaos game is a way of generating fractals through different itterative processes. By choosing a set of points and generating one random point in between the other points. If you then draw the midpoint between the coosen point and a random point in the set of points, and then use that midpoint in the next itteration of the process. The resulting fractals could look something like this:


<img src="images/ChaosGameFractal_Triangle__2000x2000-50.png" width="250"><img src="images/ChaosGameFractal_Square+Midpoints__2000x2000-66.png" width="250"><img src="images/ChaosGameFractal_Pentagon__2000x2000-61.png" width="250">

In addition it is possible to create different patterns by imposing requirements on which points the algorithm is allowed to choose. For example the first of the following fractal was created as a square with the restriction that if the same point got choosen two times in a row the following point could not neighbour the repeating point. The other two were created with the restriction that the same point couldn't be picked two times in a row.

<img src="images/ChaosGameFractal_Square_TwoInRowNotNeighbour_2000x2000-50.png" width="250"><img src="images/ChaosGameFractal_Pentagon_NotLastCorner_2000x2000-50.png" width="300"><img src="images/ChaosGameFractal_Square_NotLastCorner_2000x2000-50.png" width="250">

### Generalising with affine transformations
These algorithms use affine transformation to create selfsimilar shapes. By finetuning these transformations we can create many facsinating fractals such as the Barnsley Fern 

<img src = "images/BarnsleyFern.png" width=300>

Further reading: 
https://en.wikipedia.org/wiki/Chaos_game
https://en.wikipedia.org/wiki/Barnsley_fern

