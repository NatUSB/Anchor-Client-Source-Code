package com.anchorclient.client.util.render.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * @author Demo
 */
public class ShaderUtil {

    /**
     * Loads and compiles the specified shader
     * @param source The shader source
     * @param type The type of shader
     * @return The program ID
     */
    public static int loadShader(String source, int type) {
        // Create the shader and assign the ID.
        int shaderID = glCreateShader(type);

        // Attach the shader source code to the shader program
        glShaderSource(shaderID, source);

        // Compile the shader
        glCompileShader(shaderID);

        // Check if the shader compilation was not successful
        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println(glGetShaderInfoLog(shaderID, 500));
            throw new IllegalStateException("Could not compile shader!");
        }

        return shaderID;
    }

}

