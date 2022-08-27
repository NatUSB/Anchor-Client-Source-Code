package com.anchorclient.client.util.misc;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HoveredUtil {

	/**
	 * Checks if the text is hovered
	 * @param textWidth The width of the text
	 * @param textHeight The height of the text
	 * @param x The x position of the text
	 * @param y The y position of the text
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 * @return If the text is hovered
	 */
	public boolean isTextHovered(float textWidth, float textHeight, float x, float y, float mouseX, float mouseY) {
		return mouseX >= x - 2 && mouseX <= x + textWidth + 2 && mouseY <= y + textHeight && mouseY >= y;
	}

	/**
	 * Checks if the rectangle is hovered
	 * @param x The x position
	 * @param y The y position
	 * @param width The width
	 * @param height The height
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 * @return If the rectangle is hovered
	 */
	public boolean isRectHovered(float x, float y, float width, float height, float mouseX, float mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY <= y + height && mouseY >= y;
	}

	/**
	 * Checks if the circle is hovered
	 * @param x The x position
	 * @param y The y position
	 * @param radius The circle radius
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 * @return If the circle is hovered
	 */
	public boolean isCircleHovered(float x, float y, float radius, float mouseX, float mouseY) {
		return MathUtil.getDistance(x, y, mouseX, mouseY) <= radius;
	}
	
}
