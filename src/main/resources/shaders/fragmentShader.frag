#version 410 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
layout(std140) uniform Material {
    vec4 diffuseReflection;
//    vec4 specularReflection;
//    float specularExponent;
};

void main(void) {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDotl = dot(unitNormal, unitLightVector);
    float brightness = max(nDotl, 0.05);

    vec3 diffuse = brightness * lightColor * diffuseReflection.xyz;

    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;

    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, /* specularReflection.z */ 25.0);
    vec3 finalSpecular =  10.0 * dampedFactor * /*specularReflection.xyz*/ vec3(1.0, 0.8, 0.7);

    out_Color = vec4(diffuse, 1.0) * texture(textureSampler, pass_textureCoords) + vec4(finalSpecular, 1.0);
    out_Color = brightness * vec4(diffuseReflection) + vec4(finalSpecular, 1.0);
}