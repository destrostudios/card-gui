#import "Common/ShaderLib/GLSLCompat.glsllib"

uniform mat4 g_WorldMatrix;
uniform mat4 g_WorldViewProjectionMatrix;

varying vec2 texCoord;
varying vec3 normal;

uniform sampler2D m_DiffuseMap1;
uniform sampler2D m_DiffuseMap2;
uniform sampler2D m_DiffuseMap3;
uniform sampler2D m_DiffuseMap4;
uniform sampler2D m_DiffuseMap5;
uniform sampler2D m_DiffuseMap6;
uniform sampler2D m_DiffuseMap7;
uniform sampler2D m_DiffuseMap8;
uniform int m_DiffuseMapTilesX1;
uniform int m_DiffuseMapTilesY1;
uniform int m_DiffuseMapTilesX2;
uniform int m_DiffuseMapTilesY2;
uniform int m_DiffuseMapTilesX3;
uniform int m_DiffuseMapTilesY3;
uniform int m_DiffuseMapTilesX4;
uniform int m_DiffuseMapTilesY4;
uniform int m_DiffuseMapTilesX5;
uniform int m_DiffuseMapTilesY5;
uniform int m_DiffuseMapTilesX6;
uniform int m_DiffuseMapTilesY6;
uniform int m_DiffuseMapTilesX7;
uniform int m_DiffuseMapTilesY7;
uniform int m_DiffuseMapTilesX8;
uniform int m_DiffuseMapTilesY8;
uniform float m_DiffuseMapInterval1;
uniform float m_DiffuseMapInterval2;
uniform float m_DiffuseMapInterval3;
uniform float m_DiffuseMapInterval4;
uniform float m_DiffuseMapInterval5;
uniform float m_DiffuseMapInterval6;
uniform float m_DiffuseMapInterval7;
uniform float m_DiffuseMapInterval8;
uniform sampler2D m_FoilMap;
uniform float m_Time;
uniform float m_Distortion;
uniform vec4 m_FoilColor;
uniform float m_FoilColorSpeed;
uniform float m_FoilColorTint;
uniform float m_FoilColorIntensity;
uniform float m_ShineInterval;
uniform float m_ShineSize;
uniform float m_ShineSmoothing;
uniform float m_ShineIntensity;

#define PI 3.1415

vec2 getDiffuseTextureCoordinate(int tilesX, int tilesY, float interval) {
    float progress = (mod(m_Time, interval) / interval);
    float progressPerRow = (1.0 / tilesY);
    float progressPerTile = (progressPerRow / tilesX);
    float tileIndexX = floor(mod(progress, progressPerRow) / progressPerTile);
    float tileIndexY = floor(progress / progressPerRow);
    return vec2((tileIndexX + texCoord.x) / tilesX, (tileIndexY + texCoord.y) / tilesY);
}

vec4 getDistortedColor(float foilFactor) {
    vec4 rgb = vec4(1);
    float r = 1;
    float b = 1;

    float distortionWeight = (g_WorldViewProjectionMatrix * vec4(normal, 1.0)).x;
    float distortionFactor = (distortionWeight * foilFactor * m_Distortion);

    #ifdef DIFFUSEMAP1
        vec2 texCoord1 = getDiffuseTextureCoordinate(m_DiffuseMapTilesX1, m_DiffuseMapTilesY1, m_DiffuseMapInterval1);
        vec4 rgb1 = texture2D(m_DiffuseMap1, texCoord1);
        vec4 rgbDistorted1 = texture2D(m_DiffuseMap1, vec2(texCoord1.x + (distortionFactor / m_DiffuseMapTilesX1), texCoord1.y));
        rgb = mix(rgb, rgb1, rgb1.a);
        r = mix(r, rgbDistorted1.r, rgb1.a);
        b = mix(b, rgbDistorted1.b, rgb1.a);
    #endif

    #ifdef DIFFUSEMAP2
        vec2 texCoord2 = getDiffuseTextureCoordinate(m_DiffuseMapTilesX2, m_DiffuseMapTilesY2, m_DiffuseMapInterval2);
        vec4 rgb2 = texture2D(m_DiffuseMap2, texCoord2);
        vec4 rgbDistorted2 = texture2D(m_DiffuseMap2, vec2(texCoord2.x + (distortionFactor / m_DiffuseMapTilesX2), texCoord2.y));
        float r2 = rgbDistorted2.r;
        float b2 = rgbDistorted2.b;
        rgb = mix(rgb, rgb2, rgb2.a);
        r = mix(r, rgbDistorted2.r, rgb2.a);
        b = mix(b, rgbDistorted2.b, rgb2.a);
    #endif

    #ifdef DIFFUSEMAP3
        vec2 texCoord3 = getDiffuseTextureCoordinate(m_DiffuseMapTilesX3, m_DiffuseMapTilesY3, m_DiffuseMapInterval3);
        vec4 rgb3 = texture2D(m_DiffuseMap3, texCoord3);
        vec4 rgbDistorted3 = texture2D(m_DiffuseMap3, vec2(texCoord3.x + (distortionFactor / m_DiffuseMapTilesX3), texCoord3.y));
        rgb = mix(rgb, rgb3, rgb3.a);
        r = mix(r, rgbDistorted3.r, rgb3.a);
        b = mix(b, rgbDistorted3.b, rgb3.a);
    #endif

    #ifdef DIFFUSEMAP4
        vec2 texCoord4 = getDiffuseTextureCoordinate(m_DiffuseMapTilesX4, m_DiffuseMapTilesY4, m_DiffuseMapInterval4);
        vec4 rgb4 = texture2D(m_DiffuseMap4, texCoord4);
        vec4 rgbDistorted4 = texture2D(m_DiffuseMap4, vec2(texCoord4.x + (distortionFactor / m_DiffuseMapTilesX4), texCoord4.y));
        rgb = mix(rgb, rgb4, rgb4.a);
        r = mix(r, rgbDistorted4.r, rgb4.a);
        b = mix(b, rgbDistorted4.b, rgb4.a);
    #endif

    #ifdef DIFFUSEMAP5
        vec2 texCoord5 = getDiffuseTextureCoordinate(m_DiffuseMapTilesX5, m_DiffuseMapTilesY5, m_DiffuseMapInterval5);
        vec4 rgb5 = texture2D(m_DiffuseMap5, texCoord5);
        vec4 rgbDistorted5 = texture2D(m_DiffuseMap5, vec2(texCoord5.x + (distortionFactor / m_DiffuseMapTilesX5), texCoord5.y));
        rgb = mix(rgb, rgb5, rgb5.a);
        r = mix(r, rgbDistorted5.r, rgb5.a);
        b = mix(b, rgbDistorted5.b, rgb5.a);
    #endif

    #ifdef DIFFUSEMAP6
        vec2 texCoord6 = getDiffuseTextureCoordinate(m_DiffuseMapTilesX6, m_DiffuseMapTilesY6, m_DiffuseMapInterval6);
        vec4 rgb6 = texture2D(m_DiffuseMap6, texCoord6);
        vec4 rgbDistorted6 = texture2D(m_DiffuseMap6, vec2(texCoord6.x + (distortionFactor / m_DiffuseMapTilesX6), texCoord6.y));
        rgb = mix(rgb, rgb6, rgb6.a);
        r = mix(r, rgbDistorted6.r, rgb6.a);
        b = mix(b, rgbDistorted6.b, rgb6.a);
    #endif

    #ifdef DIFFUSEMAP7
        vec2 texCoord7 = getDiffuseTextureCoordinate(m_DiffuseMapTilesX7, m_DiffuseMapTilesY7, m_DiffuseMapInterval7);
        vec4 rgb7 = texture2D(m_DiffuseMap7, texCoord7);
        vec4 rgbDistorted7 = texture2D(m_DiffuseMap7, vec2(texCoord7.x + (distortionFactor / m_DiffuseMapTilesX7), texCoord7.y));
        rgb = mix(rgb, rgb7, rgb7.a);
        r = mix(r, rgbDistorted7.r, rgb7.a);
        b = mix(b, rgbDistorted7.b, rgb7.a);
    #endif

    #ifdef DIFFUSEMAP8
        vec2 texCoord8 = getDiffuseTextureCoordinate(m_DiffuseMapTilesX8, m_DiffuseMapTilesY8, m_DiffuseMapInterval8);
        vec4 rgb8 = texture2D(m_DiffuseMap8, texCoord8);
        vec4 rgbDistorted8 = texture2D(m_DiffuseMap8, vec2(texCoord8.x + (distortionFactor / m_DiffuseMapTilesX8), texCoord8.y));
        rgb = mix(rgb, rgb8, rgb8.a);
        r = mix(r, rgbDistorted8.r, rgb8.a);
        b = mix(b, rgbDistorted8.b, rgb8.a);
    #endif

    return vec4(r, rgb.g, b, rgb.a);
}

vec4 getFoilColor() {
    float a = 2.0 * texCoord.x;
    float b = sin((m_Time * m_FoilColorSpeed * 2.2) + 1.1 + a) + sin((m_Time * m_FoilColorSpeed * 1.8) + 0.5 - a) + sin((m_Time * m_FoilColorSpeed * 1.5) + 8.2 + 2.0 * texCoord.y) + sin((m_Time * m_FoilColorSpeed * 2.0) + 595 + 5.0 * texCoord.y);
    float c = ((5.0 + b) / 5.0) - floor(((5.0 + b) / 5.0));
    float d = c + m_FoilColor.r * 0.2 + m_FoilColor.g * 0.4 + m_FoilColor.b * 0.2;
    d = (d - floor(d)) * 8;
    vec4 rainbowColor = vec4(clamp(d - 4.0, 0.0, 1.0) + clamp(2.0 - d, 0.0, 1.0), d < 2.0 ? clamp(d, 0.0, 1.0) : clamp(4.0 - d, 0.0, 1.0), d < 4.0 ? clamp(d - 2.0, 0.0, 1.0) : clamp(6.0 - d, 0.0, 1.0), m_FoilColor.a);
    return mix(m_FoilColor, rainbowColor, m_FoilColorTint);
}

vec4 getShineColor() {
    float position = sin((m_Time / m_ShineInterval) * (2 * PI));
    vec2 uv = texCoord - vec2(position + 0.5, 0.5);
    float a = atan(uv.x, uv.y) + 1.4;
    float b = cos(floor(0.5 + a / PI) * PI - a) * length(uv);
    float c = 1.0 - smoothstep(m_ShineSize, m_ShineSize + m_ShineSmoothing, b);
    return vec4(c);
}

vec4 blend(vec4 color1, vec4 color2, float weight) {
    vec4 result = color1;
    result.a = color2.a + color1.a * (1 - color2.a);
    result.rgb = (color2.rgb * color2.a + color1.rgb * color1.a * (1 - color2.a)) * (result.a + 0.0000001);
    result.a = clamp(result.a, 0.0, 1.0);
    return mix(color1, result, weight);
}

void main(){
    float foilFactor = texture2D(m_FoilMap, texCoord).a;
    vec4 outColor = getDistortedColor(foilFactor);
    #ifdef FOILCOLOR
        outColor = blend(outColor, getFoilColor(), foilFactor * m_FoilColorIntensity);
    #endif
    #ifdef SHINEINTERVAL
        outColor = blend(outColor, getShineColor(), foilFactor * m_ShineIntensity);
    #endif
    gl_FragColor = outColor;
}
