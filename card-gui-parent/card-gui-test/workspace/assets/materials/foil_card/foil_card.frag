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
uniform int m_DiffuseMapTiles1;
uniform int m_DiffuseMapTiles2;
uniform int m_DiffuseMapTiles3;
uniform int m_DiffuseMapTiles4;
uniform int m_DiffuseMapTiles5;
uniform int m_DiffuseMapTiles6;
uniform int m_DiffuseMapTiles7;
uniform int m_DiffuseMapTiles8;
uniform float m_DiffuseMapSpeed1;
uniform float m_DiffuseMapSpeed2;
uniform float m_DiffuseMapSpeed3;
uniform float m_DiffuseMapSpeed4;
uniform float m_DiffuseMapSpeed5;
uniform float m_DiffuseMapSpeed6;
uniform float m_DiffuseMapSpeed7;
uniform float m_DiffuseMapSpeed8;
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

vec4 getDistortedColor(float foilFactor) {
    vec4 rgb = vec4(1);
    float r = 1;
    float b = 1;

    float distortionWeight = (g_WorldViewProjectionMatrix * vec4(normal, 1.0)).x;
    float distortionFactor = (distortionWeight * foilFactor * m_Distortion);

    #ifdef DIFFUSEMAP1
        float tileProgress1 = (mod(m_Time, m_DiffuseMapSpeed1) / m_DiffuseMapSpeed1);
        float tileIndex1 = floor(tileProgress1 * m_DiffuseMapTiles1);
        vec2 texCoord1 = vec2((tileIndex1 + texCoord.x) / m_DiffuseMapTiles1, texCoord.y);
        vec4 rgb1 = texture2D(m_DiffuseMap1, texCoord1);
        vec4 rgbDistorted1 = texture2D(m_DiffuseMap1, texCoord1 + vec2(distortionFactor / m_DiffuseMapTiles1, 0));
        rgb = mix(rgb, rgb1, rgb1.a);
        r = mix(r, rgbDistorted1.r, rgb1.a);
        b = mix(b, rgbDistorted1.b, rgb1.a);
    #endif

        #ifdef DIFFUSEMAP2
        float tileProgress2 = (mod(m_Time, m_DiffuseMapSpeed2) / m_DiffuseMapSpeed2);
        float tileIndex2 = floor(tileProgress2 * m_DiffuseMapTiles2);
        vec2 texCoord2 = vec2((tileIndex2 + texCoord.x) / m_DiffuseMapTiles2, texCoord.y);
        vec4 rgb2 = texture2D(m_DiffuseMap2, texCoord2);
        vec4 rgbDistorted2 = texture2D(m_DiffuseMap2, texCoord2 + vec2(distortionFactor / m_DiffuseMapTiles2, 0));
        float r2 = rgbDistorted2.r;
        float b2 = rgbDistorted2.b;
        rgb = mix(rgb, rgb2, rgb2.a);
        r = mix(r, rgbDistorted2.r, rgb2.a);
        b = mix(b, rgbDistorted2.b, rgb2.a);
    #endif

    #ifdef DIFFUSEMAP3
        float tileProgress3 = (mod(m_Time, m_DiffuseMapSpeed3) / m_DiffuseMapSpeed3);
        float tileIndex3 = floor(tileProgress3 * m_DiffuseMapTiles3);
        vec2 texCoord3 = vec2((tileIndex3 + texCoord.x) / m_DiffuseMapTiles3, texCoord.y);
        vec4 rgb3 = texture2D(m_DiffuseMap3, texCoord3);
        vec4 rgbDistorted3 = texture2D(m_DiffuseMap3, texCoord3 + vec2(distortionFactor / m_DiffuseMapTiles3, 0));
        rgb = mix(rgb, rgb3, rgb3.a);
        r = mix(r, rgbDistorted3.r, rgb3.a);
        b = mix(b, rgbDistorted3.b, rgb3.a);
    #endif

    #ifdef DIFFUSEMAP4
        float tileProgress4 = (mod(m_Time, m_DiffuseMapSpeed4) / m_DiffuseMapSpeed4);
        float tileIndex4 = floor(tileProgress4 * m_DiffuseMapTiles4);
        vec2 texCoord4 = vec2((tileIndex4 + texCoord.x) / m_DiffuseMapTiles4, texCoord.y);
        vec4 rgb4 = texture2D(m_DiffuseMap4, texCoord4);
        vec4 rgbDistorted4 = texture2D(m_DiffuseMap4, texCoord4 + vec2(distortionFactor / m_DiffuseMapTiles4, 0));
        rgb = mix(rgb, rgb4, rgb4.a);
        r = mix(r, rgbDistorted4.r, rgb4.a);
        b = mix(b, rgbDistorted4.b, rgb4.a);
    #endif

    #ifdef DIFFUSEMAP5
        float tileProgress5 = (mod(m_Time, m_DiffuseMapSpeed5) / m_DiffuseMapSpeed5);
        float tileIndex5 = floor(tileProgress5 * m_DiffuseMapTiles5);
        vec2 texCoord5 = vec2((tileIndex5 + texCoord.x) / m_DiffuseMapTiles5, texCoord.y);
        vec4 rgb5 = texture2D(m_DiffuseMap5, texCoord5);
        vec4 rgbDistorted5 = texture2D(m_DiffuseMap5, texCoord5 + vec2(distortionFactor / m_DiffuseMapTiles5, 0));
        rgb = mix(rgb, rgb5, rgb5.a);
        r = mix(r, rgbDistorted5.r, rgb5.a);
        b = mix(b, rgbDistorted5.b, rgb5.a);
    #endif

    #ifdef DIFFUSEMAP6
        float tileProgress6 = (mod(m_Time, m_DiffuseMapSpeed6) / m_DiffuseMapSpeed6);
        float tileIndex6 = floor(tileProgress6 * m_DiffuseMapTiles6);
        vec2 texCoord6 = vec2((tileIndex6 + texCoord.x) / m_DiffuseMapTiles6, texCoord.y);
        vec4 rgb6 = texture2D(m_DiffuseMap6, texCoord6);
        vec4 rgbDistorted6 = texture2D(m_DiffuseMap6, texCoord6 + vec2(distortionFactor / m_DiffuseMapTiles6, 0));
        rgb = mix(rgb, rgb6, rgb6.a);
        r = mix(r, rgbDistorted6.r, rgb6.a);
        b = mix(b, rgbDistorted6.b, rgb6.a);
    #endif

    #ifdef DIFFUSEMAP7
        float tileProgress7 = (mod(m_Time, m_DiffuseMapSpeed7) / m_DiffuseMapSpeed7);
        float tileIndex7 = floor(tileProgress7 * m_DiffuseMapTiles7);
        vec2 texCoord7 = vec2((tileIndex7 + texCoord.x) / m_DiffuseMapTiles7, texCoord.y);
        vec4 rgb7 = texture2D(m_DiffuseMap7, texCoord7);
        vec4 rgbDistorted7 = texture2D(m_DiffuseMap7, texCoord7 + vec2(distortionFactor / m_DiffuseMapTiles7, 0));
        rgb = mix(rgb, rgb7, rgb7.a);
        r = mix(r, rgbDistorted7.r, rgb7.a);
        b = mix(b, rgbDistorted7.b, rgb7.a);
    #endif

    #ifdef DIFFUSEMAP8
        float tileProgress8 = (mod(m_Time, m_DiffuseMapSpeed8) / m_DiffuseMapSpeed8);
        float tileIndex8 = floor(tileProgress8 * m_DiffuseMapTiles8);
        vec2 texCoord8 = vec2((tileIndex8 + texCoord.x) / m_DiffuseMapTiles8, texCoord.y);
        vec4 rgb8 = texture2D(m_DiffuseMap8, texCoord8);
        vec4 rgbDistorted8 = texture2D(m_DiffuseMap8, texCoord8 + vec2(distortionFactor / m_DiffuseMapTiles8, 0));
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
    #ifdef FOILMAP
        float foilFactor = texture2D(m_FoilMap, texCoord).a;
        vec4 outColor = getDistortedColor(foilFactor);
        #ifdef FOILCOLOR
            outColor = blend(outColor, getFoilColor(), foilFactor * m_FoilColorIntensity);
        #endif
        #ifdef SHINEINTERVAL
            outColor = blend(outColor, getShineColor(), foilFactor * m_ShineIntensity);
        #endif
        gl_FragColor = outColor;
    #else
        gl_FragColor = texture2D(m_DiffuseMap, texCoord);
    #endif
}
