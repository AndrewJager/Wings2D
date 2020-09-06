# Wings2D

An experimental 2D Java game framework. This project is currently very unfinished, and most of the goals listed below have not been met. 



### Goals
##### Cross-platform
This framework should use the java.awt.canvas object, which will (in theory) allow it to work on any desktop platform that supports the JVM.

##### Maintain aspect ratio
The canvas used to draw on will be automatically scaled to a 16:9 aspect ratio (custom aspect ratio support may be added in the future). This allows the developer to ensure that the presentation of the game will always be the same, while the user can change their window size to anything without having to worry about losing part of the game offscreen.

##### Automatic scaling
The framework will automatically scale all images, graphical effects, and UI with the size of the drawing canvas. As long as the developer uses the provided classes, they should not have to worry about having to manually scale anything. The game presentation should appear the same, regardless of the size of the window.

##### "Vector" graphics
All of the sprites used by a game developed with this framework are saved as shapes, and saved in a .txt format. There will be no external image files. This is to allow for perfect scaling, which would be impossible to do cleanly with bitmap graphics.

This does not mean that the framework is limited to drawing plain shapes. While the sprites are saved as shape data, they are rendered as an image. The framework will provide many "filters" that can be run when the image is created, providing for shading, lighting, outlines, and more. All filters should correctly scaling with the images, ensuring a uniform presentation.

An early version of an editor to create spritesheets for this framework is available here: https://github.com/CatsAreEvil/Wings2D-Editor

##### Simple UI
Whille this framework is based on the swing toolkit, the swing UI elements are not visually suitable for most games. Therefore, this framework will provide a few simple UI components to allow the developer to create buttons, simple menus, and text effects for added effect. All of this will scale cleanly with the drawing canvas.
