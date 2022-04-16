# Wings2D

An experimental 2D Java game framework. This project is currently very unfinished, and most of the goals listed below have not been met. 



### Goals
##### Cross-platform
This framework should use the java swing framework, which will (in theory) allow it to work on any desktop platform that supports the JVM.

##### Maintain aspect ratio
The canvas used to draw on will be automatically scaled to a fixed aspect ratio. This allows the developer to ensure that the presentation of the game will always be the same, while the user can change their window size to anything without having to worry about losing part of the game offscreen.

##### Automatic scaling
The framework will automatically scale all images, graphical effects, and UI with the size of the drawing canvas. As long as the developer uses the provided classes, they should not have to worry about having to manually scale anything. The game presentation should appear the same, regardless of the size of the window.

##### "Vector" graphics
All of the sprites used by a game developed with this framework are saved as shapes, and saved in a .txt format. There will be no external image files. This is to allow for perfect scaling, which would be impossible to do cleanly with bitmap graphics.

This does not mean that the framework is limited to drawing plain shapes. While the sprites are saved as shape data, they are rendered as an image. The framework will provide many "filters" that can be run when the image is created, providing for shading, lighting, outlines, and more. All filters should correctly scaling with the images, ensuring a uniform presentation.

An early version of an editor to create spritesheets for this framework is available here: https://github.com/CatsAreEvil/Wings2D-Editor

##### Simple Game UI
While this framework is based on the swing toolkit, the swing UI elements are not visually suitable for most games. Therefore, this framework will implement reskins of some swing controls. 

##### Accessibility
This framework will make efforts to allow developers to make their games accessible to visually impaired users. This will primarily be done in two ways:
1. Use swing controls as the base for the UI framework. This will allow the usage of built-in swing functionality that works with screen readers. Further research will be done with this to determine if this can be leveraged for non-UI game elements.
2. The graphics system will support the ability to use alternate color pallettes for all visual elements with minimal effort, which will aid developers in creating colorblind-friendly games.