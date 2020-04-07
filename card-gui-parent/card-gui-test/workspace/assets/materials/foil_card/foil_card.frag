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

void main(){
    #ifdef FOILMAP
        float foilFactor = texture2D(m_FoilMap, texCoord).a;

        // Distortion
        float distortion_factor = foilFactor * m_Distortion;
        float distortion_weight = (g_WorldViewProjectionMatrix * vec4(normal, 1.0)).x;

        vec4 distortion_rgb = texture2D(m_DiffuseMap, texCoord);
        float distortion_r = texture2D(m_DiffuseMap, texCoord + vec2(distortion_weight * distortion_factor, 0)).r;
        float distortion_b = texture2D(m_DiffuseMap, texCoord + vec2(distortion_weight * distortion_factor, 0)).b;
        vec4 outColor = vec4(distortion_r, distortion_rgb.g, distortion_b, distortion_rgb.a);

        // FoilColor
        #ifdef FOILCOLOR
            float color_a = 2.0 * texCoord.x;
            float color_b = sin((m_Time * m_FoilColorSpeed * 2.2) + 1.1 + color_a) + sin((m_Time * m_FoilColorSpeed * 1.8) + 0.5 - color_a) + sin((m_Time * m_FoilColorSpeed * 1.5) + 8.2 + 2.0 * texCoord.y) + sin((m_Time * m_FoilColorSpeed * 2.0) + 595 + 5.0 * texCoord.y);
            float color_c = ((5.0 + color_b) / 5.0) - floor(((5.0 + color_b) / 5.0));
            float color_d = color_c + m_FoilColor.r * 0.2 + m_FoilColor.g * 0.4 + m_FoilColor.b * 0.2;
            color_d = (color_d - floor(color_d)) * 8;
            vec4 color_pure = vec4(clamp(color_d - 4.0, 0.0, 1.0) + clamp(2.0 - color_d, 0.0, 1.0), color_d < 2.0 ? clamp(color_d, 0.0, 1.0) : clamp(4.0 - color_d, 0.0, 1.0), color_d < 4.0 ? clamp(color_d - 2.0, 0.0, 1.0) : clamp(6.0 - color_d, 0.0, 1.0), m_FoilColor.a);
            vec4 color_result = mix(m_FoilColor, color_pure, m_FoilColorTint);

            float color_blendWeight = foilFactor * m_FoilColorIntensity;
            vec4 color_mixed = outColor;
            color_mixed.a = color_result.a + outColor.a * (1 - color_result.a);
            color_mixed.rgb = (color_result.rgb * color_result.a + outColor.rgb * outColor.a * (1 - color_result.a)) * (color_mixed.a + 0.0000001);
            color_mixed.a = clamp(color_mixed.a, 0.0, 1.0);
            outColor = mix(outColor, color_mixed, color_blendWeight);
        #endif

        // Shine
        #ifdef SHINEINTERVAL
            float shine_position = sin((m_Time / m_ShineInterval) * (2 * PI));
            vec2 shine_uv = texCoord - vec2(shine_position + 0.5, 0.5);
            float shine_a = atan(shine_uv.x, shine_uv.y) + 1.4;
            float shine_c = cos(floor(0.5 + shine_a / PI) * PI - shine_a) * length(shine_uv);
            float shine_d = 1.0 - smoothstep(m_ShineSize, m_ShineSize + m_ShineSmoothing, shine_c);
            vec4 shine_result = vec4(shine_d);

            float shine_blendWeight = foilFactor * m_ShineIntensity;
            vec4 shine_mixed = outColor;
            shine_mixed.a = shine_result.a + outColor.a * (1 - shine_result.a);
            shine_mixed.rgb = (shine_result.rgb * shine_result.a + outColor.rgb * outColor.a * (1 - shine_result.a)) * (shine_mixed.a + 0.0000001);
            shine_mixed.a = clamp(shine_mixed.a, 0.0, 1.0);
            outColor = mix(outColor, shine_mixed, shine_blendWeight);
        #endif

        gl_FragColor = outColor;
    #else
        gl_FragColor = texture2D(m_DiffuseMap, texCoord);
    #endif
}
