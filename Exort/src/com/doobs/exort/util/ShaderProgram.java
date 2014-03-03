package com.doobs.exort.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.*;

public class ShaderProgram {
	public int program;
	public int vertexShader, fragmentShader;

	public ShaderProgram(String URL) {
		program = glCreateProgram();
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		String vertexSource = "";
		String fragmentSource = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					"res/shaders/" + URL + ".vert")));
			String line;
			for (int i = 0; i < 2; i++) {
				if (i == 1)
					reader = new BufferedReader(new FileReader(new File(
							"res/shaders/" + URL + ".frag")));
				while ((line = reader.readLine()) != null) {
					if (i == 0)
						vertexSource += line + "\n";
					else if (i == 1)
						fragmentSource += line + "\n";
				}
			}
			glShaderSource(vertexShader, vertexSource);
			glCompileShader(vertexShader);
			if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
				System.err.println("Vertex shader not compiled.");
				System.err.println(glGetShaderInfoLog(vertexShader, 1024));
			}
			glShaderSource(fragmentShader, fragmentSource);
			glCompileShader(fragmentShader);
			if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
				System.err.println("Fragment shader not compiled.");
				System.err.println(glGetShaderInfoLog(fragmentShader, 1024));
			}
			glAttachShader(program, vertexShader);
			glAttachShader(program, fragmentShader);
			glLinkProgram(program);
			glValidateProgram(program);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cleanup() {
		glDeleteProgram(program);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}
}
