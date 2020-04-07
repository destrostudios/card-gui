#import "Common/ShaderLib/GLSLCompat.glsllib"

uniform mat4 g_WorldMatrix;
uniform mat4 g_WorldViewProjectionMatrix;

varying vec2 texCoord;
varying vec3 normal;

uniform sampler2D m_DiffuseMap;
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
    float factor = foilFactor * m_Distortion;
    float weight = (g_WorldViewProjectionMatrix * vec4(normal, 1.0)).x;

    vec4 rgb = texture2D(m_DiffuseMap, texCoord);
    float r = texture2D(m_DiffuseMap, texCoord + vec2(weight * factor, 0)).r;
    float b = texture2D(m_DiffuseMap, texCoord + vec2(weight * factor, 0)).b;
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
