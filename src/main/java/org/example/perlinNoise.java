package org.example;

import java.util.Random;

class perlinNoise{
    public static float[][] GenerateWhiteNoise(int sirka,int vyska,long seed){
        Random rand = new Random(seed);
        float[][] noise = new float[sirka][vyska];
        for (int i = 0; i < sirka; i++){
            for (int j = 0; j < vyska; j++){
                noise[i][j] = (float)rand.nextDouble();
            }
        }
        return noise;
    }


    static float[][] GenerateSmoothNoise(float[][] baseNoise, int octave)
    {
        int sirka = baseNoise.length;
        int vyska = baseNoise[0].length;

        float[][] smoothNoise = new float[sirka][vyska];

        int samplePeriod = (int) Math.pow(2, octave); // calculates 2 ^ k
        float sampleFrequency = 1.0f / samplePeriod;

        for (int i = 0; i < sirka; i++)
        {
            //calculate the horizontal sampling indices
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % sirka; //wrap around
            float horizontalBlend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < vyska; j++)
            {
                //calculate the vertical sampling indices
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % vyska; //wrap around
                float verticalBlend = (j - sample_j0) * sampleFrequency;

                //blend the top two corners
                float top = Interpolate(baseNoise[sample_i0][sample_j0],
                        baseNoise[sample_i1][sample_j0], horizontalBlend);

                //blend the bottom two corners
                float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
                        baseNoise[sample_i1][sample_j1], horizontalBlend);

                //final blend
                smoothNoise[i][j] = Interpolate(top, bottom, verticalBlend);
            }
        }

        return smoothNoise;
    }

    static float Interpolate(float x0, float x1, float alpha)
    {
        double cosine = (1.0 - Math.cos(alpha * Math.PI)) / 2.0;
        return (float) (x0 * (1 - cosine) + cosine * x1);
    }

    public static float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount)
    {
        int sirka = baseNoise.length;
        int vyska = baseNoise[0].length;

        float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing

        float persistance = 0.55f;

        //generate smooth noise
        for (int i = 0; i < octaveCount; i++)
        {
            smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
        }

        float[][] perlinNoise = new float[sirka][vyska];
        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        //blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--)
        {
            amplitude *= persistance;
            totalAmplitude += amplitude;

            for (int i = 0; i < sirka; i++)
            {
                for (int j = 0; j < vyska; j++)
                {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        //normalisation
        for (int i = 0; i < sirka; i++)
        {
            for (int j = 0; j < vyska; j++)
            {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }


    public static float[][] WholeNoise(String seed,int sirka, int vyska,int octaveCount){
        return GeneratePerlinNoise(GenerateWhiteNoise(sirka,vyska,garbagefunctions.Seed2Long(seed)),octaveCount);
    }

}
