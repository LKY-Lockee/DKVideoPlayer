package xyz.doikki.dkplayer.widget.render.gl2.filter;

import android.opengl.GLES20;

/**
 * Modified by LKY-Lockee on 2026/6/22
 */
public class GlPosterizeFilter extends GlFilter {

    private static final String POSTERIZE_FRAGMENT_SHADER = "precision mediump float;" +
            " varying vec2 vTextureCoord;\n" +
            "\n" +
            "uniform lowp sampler2D sTexture;\n" +
            "uniform highp float colorLevels;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "   highp vec4 textureColor = texture2D(sTexture, vTextureCoord);\n" +
            "   \n" +
            "   gl_FragColor = floor((textureColor * colorLevels) + vec4(0.5)) / colorLevels;\n" +
            "}";

    public GlPosterizeFilter() {
        super(DEFAULT_VERTEX_SHADER, POSTERIZE_FRAGMENT_SHADER);
    }

    private int colorLevels = 10;

    public void setColorLevels(int colorLevels) {
        if (colorLevels < 0) {
            this.colorLevels = 0;
        } else this.colorLevels = Math.min(colorLevels, 256);
    }

    @Override
    public void onDraw() {
        GLES20.glUniform1f(getHandle("colorLevels"), colorLevels);
    }
}
