package com.anchorclient.client.util.render.font;

import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class TextureData {

    private final int textureId, width, height;
    private final ByteBuffer buffer;

    public TextureData(int textureId, int width, int height, ByteBuffer buffer) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.buffer = buffer;
    }

}