package com.anchorclient.client.util.render.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * @author Demo
 */
public class Shader {

    public final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    public Shader(String vertex, String fragment) {
        // Load and compile the shaders then assign their ID's
        vertexShaderID = ShaderUtil.loadShader(vertex, GL_VERTEX_SHADER);
        fragmentShaderID = ShaderUtil.loadShader(fragment, GL_FRAGMENT_SHADER);

        // Create a new program and assign its ID
        programID = glCreateProgram();

        // Attach the shaders to the program
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);

        // Link and validate the program
        glLinkProgram(programID);
        glValidateProgram(programID);
    }

    /**
     * Starts the shader
     */
    public void start() {
        glUseProgram(programID);
    }

    /**
     * Stops the shader
     */
    public void stop() {
        glUseProgram(0);
    }

    /**
     * Gets the location in memory of the specified uniform
     * @param name The uniform name
     * @return The location in memory
     */
    public int getUniform(String name) {
        int location = glGetUniformLocation(programID, name);

        if(location == -1) {
            throw new IllegalStateException("Shader uniform variable '" + name + "' not found!");
        }

        return location;
    }

    /**
     Sets the value of the specified uniform variable
     * @param name The uniform name
     * @param x The x value
     * @param y The y value
     * @param z the z value
     * @param w the w value
     */
    public void setUniform4f(String name, float x, float y, float z, float w) {
        glUniform4f(getUniform(name), x, y, z, w);
    }

    /**
     Sets the value of the specified uniform variable
     * @param name The uniform name
     * @param x The x value
     * @param y The y value
     * @param z the z value
     */
    public void setUniform3f(String name, float x, float y, float z) {
        glUniform3f(getUniform(name), x, y, z);
    }

    /**
     Sets the value of the specified uniform variable
     * @param name The uniform name
     * @param x The x value
     * @param y The y value
     */
    public void setUniform2f(String name, float x, float y) {
        glUniform2f(getUniform(name), x, y);
    }

    /**
     Sets the value of the specified uniform variable
     * @param name The uniform name
     * @param x The x value
     */
    public void setUniform1f(String name, float x) {
        glUniform1f(getUniform(name), x);
    }

    public void setUniform4i(String name, int x, int y, int z, int w) {
        glUniform4i(getUniform(name), x, y, z, w);
    }

    public void setUniform3i(String name, int x, int y, int z) {
        glUniform3i(getUniform(name), x, y, z);
    }

    public void setUniform2i(String name, int x, int y) {
        glUniform2i(getUniform(name), x, y);
    }

    public void setUniform1i(String name, int x) {
        glUniform1i(getUniform(name), x);
    }

    public static void drawCanvas(float x, float y, float width, float height) {
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glEnd();
    }

    /**
     * Draws the canvas that the shader is rendered on
     */
    public static void drawCanvas() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float width = (float) sr.getScaledWidth();
        float height = (float) sr.getScaledHeight();
        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, height);
        glTexCoord2f(1, 0);
        glVertex2f(width, height);
        glTexCoord2f(1, 1);
        glVertex2f(width, 0);
        glEnd();
    }

}
