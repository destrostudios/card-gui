void main(){
    outColor = texture2D(m_GlowMap, texCoord) * m_Color;
    outColor.a *= m_Alpha;
}