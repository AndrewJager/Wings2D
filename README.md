# Wings2D

An experimental 2D Java game framework, using Swing and vector graphics. This project is currently very unfinished, and most of the goals listed below have not been met. 


### Goals
##### Vector graphics
This framework will parse .svg files, and use the Graphics2D class from the JDK to draw them as shapes, rather than relying on images where we have to worry about how the pixels are scaled to the user's monitor. 

Only .svg attributes that can be easily rendered by Graphics2D will be supported. Warnings will be given to the user if they attempt to import a .svg with unsupported attributes.

##### Cross-platform
This framework will use the java swing framework, which will (in theory) allow it to work on any desktop platform that supports the JVM.

##### Maintain aspect ratio
The canvas used to draw on will be automatically scaled to a fixed aspect ratio. This allows the developer to ensure that the presentation of the game will always be the same, while the user can change their window size to anything without having to worry about losing part of the game offscreen.

##### Automatic scaling
The framework will automatically scale all vector graphics, and UI with the size of the drawing canvas. As long as the developer uses the provided classes, they should not have to worry about having to manually scale anything. The game presentation should appear the same, regardless of the size of the window.
