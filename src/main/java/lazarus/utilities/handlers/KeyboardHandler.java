package lazarus.utilities.handlers;

//General place to store keyboard press events.

import org.lwjgl.input.Keyboard;

public class KeyboardHandler {

	//Call these to detect if a key is pressed. To add more keys, follow the same format.

	public static boolean isShiftDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}

	public static boolean isControlDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
	}
	
	public static boolean isSpaceDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_SPACE) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
	}
}