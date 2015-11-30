package hp.kuet.musta;


import android.content.Context;
import android.graphics.Shader;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
//import static android.opengl.GLES20.glClear;
//import static android.opengl.GLES20.glClearColor;
//import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.*;
//import static android.opengl.GLUtils.*;
//import static android.opengl.Matrix.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import hp.kuet.musta.util.LoggerConfig;
import hp.kuet.musta.util.ShaderHelper;
import hp.kuet.musta.util.TextResourceReader;


/**
 * Created by MUSTA on 11/25/2015.
 */
public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private int uColorLocation;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private final Context context;
    private int program;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;

    public AirHockeyRenderer( Context context){
        this.context = context;


        float[] tableVerticesWithTriangles = {

                //30 triangle 1
                (float) (-.18*5), (float) (.033*5),
                //29
                (float) (-.15*5), (float) (.032*5),
                //31
                (float) (-.16*5), (float) (.015*5),



                //triangle 2

                (float) (-.15*5), (float) (.032*5),
                //31
                (float) (-.16*5), (float) (.015*5),
                //32
                (float) (-.145*5), (float) (.015*5),



                //29 triangle 3
                (float) (-.15*5), (float) (.032*5),
                //32
                (float) (-.145*5), (float) (.015*5),
                //33
                (float) (-.14*5), (float) (.02*5),



                //29 triangle 4
                (float) (-.15*5), (float) (.032*5),
                //33
                (float) (-.14*5), (float) (.02*5),
                //28
                (float) (-.14*5), (float) (.04*5),



                //28 triangle 5
                (float) (-.14*5), (float) (.04*5),
                //33
                (float) (-.14*5), (float) (.02*5),
                //34
                (float) (-.12*5), (float) (.035*5),



                //28 triangle 6
                (float) (-.14*5), (float) (.04*5),
                //34
                (float) (-.12*5), (float) (.035*5),
                //27
                (float) (-.14*5), (float) (.06*5),



                //27 triangle 7
                (float) (-.14*5), (float) (.06*5),
                //34
                (float) (-.12*5), (float) (.035*5),
                //26
                (float) (-.126*5), (float) (.075*5),



                //26 triangle 8
                (float) (-.126*5), (float) (.075*5),
                //34
                (float) (-.12*5), (float) (.035*5),
                //25
                (float) (-.124*5), (float) (.075*5),



                //25 triangle 9
                (float) (-.124*5), (float) (.075*5),
                //34
                (float) (-.12*5), (float) (.035*5),
                //35
                (float) (-.1*5), (float) (.04*5),




                //25 triangle 10
                (float) (-.124*5), (float) (.075*5),
                //35
                (float) (-.1*5), (float) (.04*5),
                //24
                (float) (-.09*5), (float) (.045*5),



                //36 triangle 11
                (float) (-.09*5), (float) (.03*5),
                //35
                (float) (-.1*5), (float) (.04*5),
                //24
                (float) (-.09*5), (float) (.045*5),



                //36 triangle 12
                (float) (-.09*5), (float) (.03*5),
                //23
                (float) (-.08*5), (float) (.045*5),
                //24
                (float) (-.09*5), (float) (.045*5),



                //36 triangle 13
                (float) (-.09*5), (float) (.03*5),
                //23
                (float) (-.08*5), (float) (.045*5),
                //22
                (float) (-.05*5), (float) (.055*5),



                //36 triangle 14
                (float) (-.09*5), (float) (.03*5),
                //21
                (float) (-.03*5), (float) (.05*5),
                //22
                (float) (-.05*5), (float) (.055*5),



                //36 triangle 15
                (float) (-.09*5), (float) (.03*5),
                //21
                (float) (-.03*5), (float) (.05*5),
                //37
                (float) (-.094*5), (float) (.02*5),



                //21 triangle 16
                (float) (-.03*5), (float) (.05*5),
                //38
                (float) (-.09*5), 0.0f,
                //37
                (float) (-.094*5), (float) (.02*5),


                //21 triangle 17
                (float) (-.03*5), (float) (.05*5),
                //38
                (float) (-.09*5), 0.0f,
                //39
                (float) (-.075*5), (float) (-.02*5),


                //21 triangle 18
                (float) (-.03*5), (float) (.05*5),
                //53
                (float) (-.035*5), (float) (-.01*5),
                //39
                (float) (-.075*5), (float) (-.02*5),


                //52 triangle 19
                (float) (-.045*5), (float) (-.025*5),
                //53
                (float) (-.035*5), (float) (-.01*5),
                //39
                (float) (-.075*5), (float) (-.02*5),


                //52 triangle 20
                (float) (-.045*5), (float) (-.025*5),
                //40
                (float) (-.086*5), (float) (-.03*5),
                //39
                (float) (-.075*5), (float) (-.02*5),



                //52 triangle 21
                (float) (-.045*5), (float) (-.025*5),
                //40
                (float) (-.086*5), (float) (-.03*5),
                //51
                (float) (-.07*5), (float) (-.05*5),



                //41 triangle 22
                (float) (-.095*5), (float) (-.05*5),
                //40
                (float) (-.086*5), (float) (-.03*5),
                //51
                (float) (-.07*5), (float) (-.05*5),



                //41 triangle 23
                (float) (-.095*5), (float) (-.05*5),
                //50
                (float) (-.073*5), (float) (-.064*5),
                //51
                (float) (-.07*5), (float) (-.05*5),



                //41 triangle 24
                (float) (-.095*5), (float) (-.05*5),
                //50
                (float) (-.073*5), (float) (-.064*5),
                //49
                (float) (-.073*5), (float) (-.064*5),



                //41 triangle 25
                (float) (-.095*5), (float) (-.05*5),
                //42
                (float) (-.08*5), (float) (-.099*5),
                //49
                (float) (-.073*5), (float) (-.064*5),



                //48 triangle 26
                (float) (-.065*5), (float) (-.08*5),
                //42
                (float) (-.08*5), (float) (-.099*5),
                //49
                (float) (-.073*5), (float) (-.064*5),



                //48 triangle 27
                (float) (-.065*5), (float) (-.08*5),
                //42
                (float) (-.08*5), (float) (-.099*5),
                //47
                (float) (-.06*5), (float) (-.09*5),


                //46 triangle 28
                (float) (-.065*5), (float) (-.11*5),
                //42
                (float) (-.08*5), (float) (-.099*5),
                //47
                (float) (-.06*5), (float) (-.09*5),


                //46 triangle 29
                (float) (-.065*5), (float) (-.11*5),
                //42
                (float) (-.08*5), (float) (-.099*5),
                //43
                (float) (-.083*5), (float) (-.104*5),


                //46 triangle 30
                (float) (-.065*5), (float) (-.11*5),
                //44
                (float) (-.08*5), (float) (-.118*5),
                //43
                (float) (-.083*5), (float) (-.104*5),



                //46 triangle 31
                (float) (-.065*5), (float) (-.11*5),
                //44
                (float) (-.08*5), (float) (-.118*5),
                //45
                (float) (-.075*5), (float) (-.123*5),


                //59 triangle 32
                (float) (-.047*5), (float) (-.104*5),
                //61
                (float) (-.047*5), (float) (-.1*5),
                //60
                (float) (-.043*5), (float) (-.1*5),


                //59 triangle 33 (2)
                (float) (-.047*5), (float) (-.104*5),
                //58
                (float) (-.04*5), (float) (-.114*5),
                //60
                (float) (-.043*5), (float) (-.1*5),


                //57 triangle 33
                (float) (-.03*5), (float) (-.11*5),
                //58
                (float) (-.04*5), (float) (-.114*5),
                //60
                (float) (-.043*5), (float) (-.1*5),


                //57 triangle 34
                (float) (-.03*5), (float) (-.11*5),
                //56
                (float) (-.03*5), (float) (-.083*5),
                //60
                (float) (-.043*5), (float) (-.1*5),


                //62 triangle 35
                (float) (-.044*5), (float) (-.09*5),
                //56
                (float) (-.03*5), (float) (-.083*5),
                //60
                (float) (-.043*5), (float) (-.1*5),


                //62 triangle 36
                (float) (-.044*5), (float) (-.09*5),
                //56
                (float) (-.03*5), (float) (-.083*5),
                //55
                (float) (-.055*5), (float) (-.06*5),


                //62 triangle 37
                (float) (-.044*5), (float) (-.09*5),
                //63
                (float) (-.047*5), (float) (-.09*5),
                //55
                (float) (-.055*5), (float) (-.06*5),


                //50 triangle 37(2)
                (float) (-.073*5), (float) (-.064*5),
                //63
                (float) (-.047*5), (float) (-.09*5),
                //55
                (float) (-.055*5), (float) (-.06*5),


                //50 triangle 38
                (float) (-.073*5), (float) (-.064*5),
                //51
                (float) (-.07*5), (float) (-.05*5),
                //55
                (float) (-.055*5), (float) (-.06*5),


                //52 triangle 39
                (float) (-.045*5), (float) (-.025*5),
                //51
                (float) (-.07*5), (float) (-.05*5),
                //55
                (float) (-.055*5), (float) (-.06*5),


                //52 triangle 40
                (float) (-.045*5), (float) (-.025*5),
                //54
                (float) (-.02*5), (float) (-.025*5),
                //55
                (float) (-.055*5), (float) (-.06*5),


                //52 triangle 41
                (float) (-.045*5), (float) (-.025*5),
                //54
                (float) (-.02*5), (float) (-.025*5),
                //53
                (float) (-.035*5), (float) (-.01*5),


                //52 triangle 42
                (float) (-.045*5), (float) (-.025*5),
                //114
                (float) (-.045*5), (float) (-.015*5),
                //53
                (float) (-.035*5), (float) (-.01*5),


                //21 triangle 43
                (float) (-.03*5), (float) (.05*5),
                //114
                (float) (-.045*5), (float) (-.015*5),
                //53
                (float) (-.045*5), (float) (-.01*5),


                //21 triangle 44
                (float) (-.03*5), (float) (.05*5),
                //20
                (float) (.02*5), (float) (.04*5),
                //53
                (float) (-.035*5), (float) (-.01*5),


                //65 triangle 45
                (float) (.02*5), (float) (-.04*5),
                //20
                (float) (.02*5), (float) (.04*5),
                //53
                (float) (-.045*5), (float) (-.01*5),


                //65 triangle 46
                (float) (.02*5), (float) (-.04*5),
                //64
                (float) (.01*5), (float) (-.035*5),
                //54
                (float) (-.02*5), (float) (-.025*5),

                //65 triangle 47
                (float) (.02*5), (float) (-.04*5),
                //53
                (float) (-.045*5), (float) (-.01*5),
                //54
                (float) (-.02*5), (float) (-.025*5),


                //54 triangle 48
                (float) (-.02*5), (float) (-.025*5),
                //64
                (float) (.01*5), (float) (-.037*5),
                //53
                (float) (-.045*5), (float) (-.01*5),


                //65 triangle 49
                (float) (.02*5), (float) (-.04*5),
                //20
                (float) (.02*5), (float) (.04*5),
                //66
                (float) (.043*5), (float) (-.043*5),


                //202 triangle 50
                (float) (.035*5), (float) (.045*5),
                //20
                (float) (.02*5), (float) (.04*5),
                //66
                (float) (.043*5), (float) (-.043*5),


                //202 triangle 51
                (float) (.035*5), (float) (.045*5),
                //92
                (float) (.061*5), (float) (-.045*5),
                //66
                (float) (.043*5), (float) (-.043*5),


                //74 triangle 52
                (float) (.03*5), (float) (-.118*5),
                //69
                (float) (.02*5), (float) (-.102*5),
                //73
                (float) (.02*5), (float) (-.115*5),


                //69 triangle 53
                (float) (.02*5), (float) (-.102*5),
                //74
                (float) (.03*5), (float) (-.118*5),
                //75
                (float) (.03*5), (float) (-.105*5),


                //69 triangle 54
                (float) (.02*5), (float) (-.102*5),
                //68
                (float) (.028*5), (float) (-.1*5),
                //75
                (float) (.03*5), (float) (-.105*5),

                //76 triangle 55
                (float) (.039*5), (float) (-.103*5),
                //68
                (float) (.028*5), (float) (-.1*5),
                //75
                (float) (.03*5), (float) (-.105*5),

                //67 triangle 56
                (float) (.041*5), (float) (-.084*5),
                //68
                (float) (.028*5), (float) (-.1*5),
                //76
                (float) (.039*5), (float) (-.103*5),


                //67 triangle 57
                (float) (.041*5), (float) (-.084*5),
                //76
                (float) (.039*5), (float) (-.103*5),
                //77
                (float) (.05*5), (float) (-.09*5),


                //67 triangle 58
                (float) (.041*5), (float) (-.084*5),
                //77
                (float) (.05*5), (float) (-.09*5),
                //78
                (float) (.058*5), (float) (-.08*5),


                //67 triangle 59
                (float) (.041*5), (float) (-.084*5),
                //78
                (float) (.058*5), (float) (-.08*5),
                //79
                (float) (.049*5), (float) (-.07*5),


                //66 triangle 60
                (float) (.043*5), (float) (-.043*5),
                //78
                (float) (.058*5), (float) (-.08*5),
                //79
                (float) (.049*5), (float) (-.07*5),


                //66 triangle 61
                (float) (.043*5), (float) (-.043*5),
                //78
                (float) (.058*5), (float) (-.08*5),
                //92
                (float) (.061*5), (float) (-.045*5),


                //92 triangle 62
                (float) (.061*5), (float) (-.045*5),
                //94
                (float) (.08*5), (float) (-.035*5),
                //91
                (float) (.075*5), (float) (-.045*5),


                //202 triangle 63
                (float) (.035*5), (float) (.045*5),
                //92
                (float) (.061*5), (float) (-.045*5),
                //94
                (float) (.08*5), (float) (-.035*5),



                //94 triangle 64
                (float) (.08*5), (float) (-.035*5),
                //91
                (float) (.075*5), (float) (-.045*5),
                //90
                (float) (.096*5), (float) (-.064*5),


                //94 triangle 65
                (float) (.08*5), (float) (-.035*5),
                //93
                (float) (.09*5), (float) (-.035*5),
                //90
                (float) (.096*5), (float) (-.064*5),


                //88 triangle 66
                (float) (.11*5), (float) (-.065*5),
                //93
                (float) (.09*5), (float) (-.035*5),
                //90
                (float) (.096*5), (float) (-.064*5),


                //88 triangle 67
                (float) (.11*5), (float) (-.065*5),
                //89
                (float) (.097*5), (float) (-.066*5),
                //90
                (float) (.096*5), (float) (-.064*5),


                //88 well triangle 68
                (float) (.11*5), (float) (-.065*5),
                //89
                (float) (.097*5), (float) (-.066*5),
                //87
                (float) (.11*5), (float) (-.075*5),


                //86 triangle 69
                (float) (.08*5), (float) (-.095*5),
                //89
                (float) (.097*5), (float) (-.066*5),
                //87
                (float) (.11*5), (float) (-.075*5),


                //86 triangle 70
                (float) (.08*5), (float) (-.095*5),
                //82
                (float) (.095*5), (float) (-.105*5),
                //87
                (float) (.11*5), (float) (-.075*5),


                //86 triangle 71
                (float) (.08*5), (float) (-.095*5),
                //82
                (float) (.095*5), (float) (-.105*5),
                //84
                (float) (.071*5), (float) (-.101*5),


                //84 triangle 72
                (float) (.071*5), (float) (-.101*5),
                //82
                (float) (.095*5), (float) (-.105*5),
                //81
                (float) (.07*5), (float) (-.11*5),


                //84 triangle 73
                (float) (.071*5), (float) (-.101*5),
                //85
                (float) (.069*5), (float) (-.099*5),
                //81
                (float) (.07*5), (float) (-.11*5),


                //80 triangle 74
                (float) (.065*5), (float) (-.115*5),
                //85
                (float) (.069*5), (float) (-.099*5),
                //81
                (float) (.07*5), (float) (-.11*5),


                //80 triangle 75
                (float) (.065*5), (float) (-.115*5),
                //85
                (float) (.069*5), (float) (-.099*5),
                //81
                (float) (.07*5), (float) (-.11*5),


                //80 front side legs completed triangle 76
                (float) (.065*5), (float) (-.115*5),
                //85
                (float) (.069*5), (float) (-.099*5),
                //83
                (float) (.06*5), (float) (-.1*5),


                //93 triangle 77
                (float) (.09*5), (float) (-.035*5),
                //96
                (float) (.095*5), (float) (-.01*5),
                //95
                (float) (.105*5), (float) (-.02*5),

                //93 triangle 78
                (float) (.09*5), (float) (-.035*5),
                //96
                (float) (.095*5), (float) (-.01*5),
                //94
                (float) (.08*5), (float) (-.035*5),

                //202 triangle 79
                (float) (.035*5), (float) (.045*5),
                //96
                (float) (.095*5), (float) (-.01*5),
                //94
                (float) (.08*5), (float) (-.035*5),


                //96 triangle 80
                (float) (.095*5), (float) (-.01*5),
                //95
                (float) (.105*5), (float) (-.02*5),
                //97
                (float) (.105*5), (float) (.01*5),


                //96 triangle 81
                (float) (.095*5), (float) (-.01*5),
                //202
                (float) (.035*5), (float) (.045*5),
                //97
                (float) (.105*5), (float) (.01*5),


                //100 triangle 82
                (float) (.115*5), (float) (.04*5),
                //202
                (float) (.035*5), (float) (.045*5),
                //97
                (float) (.105*5), (float) (.01*5),


                //100 triangle 83
                (float) (.115*5), (float) (.04*5),
                //202
                (float) (.035*5), (float) (.045*5),
                //18
                (float) (.055*5), (float) (.05*5),


                //13 triangle 84
                (float) (.105*5), (float) (.086*5),
                //202
                (float) (.035*5), (float) (.045*5),
                //18
                (float) (.055*5), (float) (.05*5),


                //13 triangle 85
                (float) (.105*5), (float) (.086*5),
                //202
                (float) (.035*5), (float) (.045*5),
                //14
                (float) (.085*5), (float) (.09*5),


                //15 triangle 86
                (float) (.08*5), (float) (.085*5),
                //16
                (float) (.07*5), (float) (.075*5),
                //17
                (float) (.06*5), (float) (.075*5),


                //100 triangle 87
                (float) (.115*5), (float) (.04*5),
                //202
                (float) (.035*5), (float) (.045*5),
                //13
                (float) (.105*5), (float) (.085*5),


                //100 88
                (float) (.115*5), (float) (.04*5),
                //101
                (float) (.13*5), (float) (.05*5),
                //13
                (float) (.105*5), (float) (.085*5),





                //11 triangle completed
                (float) (.125*5), (float) (.1*5),
                //13
                (float) (.105*5), (float) (.085*5),

                //101
                (float) (.13*5), (float) (.05*5),
                //113
                (float) (.157*5), (float) (.04*5),

                //11
                (float) (.125*5), (float) (.1*5),
                //6
                (float) (.135*5), (float) (.1*5),

                //3
                (float) (.145*5), (float) (.09*5),
                //6
                (float) (.135*5), (float) (.1*5),


                //3
                (float) (.145*5), (float) (.09*5),
                //2
                (float) (.17*5), (float) (.054*5),

                //106
                (float) (.165*5), (float) (.048*5),
                //2
                (float) (.17*5), (float) (.054*5),

                //2
                (float) (.17*5), (float) (.054*5),
                //107
                (float) (.17*5), (float) (.042*5),

                //107
                (float) (.17*5), (float) (.042*5),
                //106
                (float) (.165*5), (float) (.048*5),

                //107
                (float) (.17*5), (float) (.042*5),
                //108
                (float) (.17*5), (float) (.03*5),

                //109
                (float) (.16*5), (float) (.04*5),
                //110
                (float) (.169*5), (float) (.03*5),

                //109
                (float) (.16*5), (float) (.04*5),
                //113
                (float) (.157*5), (float) (.04*5),

                //112
                (float) (.159*5), (float) (.035*5),
                //113
                (float) (.157*5), (float) (.04*5),

                //112
                (float) (.159*5), (float) (.035*5),
                //111
                (float) (.161*5), (float) (.032*5),

                //110
                (float) (.169*5), (float) (.03*5),
                //111
                (float) (.161*5), (float) (.032*5),


                //102
                (float) (.135*5), (float) (.065*5),
                //103
                (float) (.143*5), (float) (.06*5),

                //104
                (float) (.152*5), (float) (.058*5),
                //103
                (float) (.143*5), (float) (.06*5),

                //104 good
                (float) (.152*5), (float) (.058*5),
                //105
                (float) (.16*5), (float) (.05*5),


                //115
                (float) (.147*5), (float) (.08*5),
                //116
                (float) (.144*5), (float) (.077*5),

                //118
                (float) (.14*5), (float) (.08*5),
                //116
                (float) (.144*5), (float) (.077*5),

                //6
                (float) (.135*5), (float) (.1*5),
                //5
                (float) (.14*5), (float) (.1*5),

                //5
                (float) (.14*5), (float) (.1*5),
                //3
                (float) (.145*5), (float) (.09*5),

                //3
                (float) (.145*5), (float) (.09*5),
                //4
                (float) (.15*5), (float) (.105*5),

                //4
                (float) (.15*5), (float) (.105*5),
                //5
                (float) (.14*5), (float) (.1*5),

                //6
                (float) (.135*5), (float) (.1*5),
                //9
                (float) (.13*5), (float) (.115*5),

                //9
                (float) (.13*5), (float) (.115*5),
                //11
                (float) (.125*5), (float) (.1*5),



        };





        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (LoggerConfig.ON){
            ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

        @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        }


        @Override
        public void onDrawFrame(GL10 gl) {
                glClear(GL_COLOR_BUFFER_BIT);

        //triangle 1
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        //triangle 2
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 3, 3);

        //triangle 3
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 6, 3);

        //triangle 4
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 9, 3);

        //triangle 5
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 12, 3);

        //triangle 6
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 15, 3);

        //triangle 7
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 18, 3);

        //triangle 8
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 21, 3);

        //triangle 9
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 24, 3);

        //triangle 10
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 27, 3);

        //triangle 11
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 30, 3);

        //triangle 12
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 33, 3);

        //triangle 13
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 36, 3);

        //triangle14
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 39, 3);

        //triangle 15
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 42, 3);

        //triangle 16
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 45, 3);

        //triangle 17
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 48, 3);

        //triangle 18
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 51, 3);

        //triangle 19
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 54, 3);

        //triangle 20
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 57, 3);

        //triangle 21
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 60, 3);

        //triangle 22
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 63, 3);

        //triangle 23
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 66, 3);

        //triangle 24
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 69, 3);

        //triangle 25
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 72, 3);

        //triangle 26
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 75, 3);


        //triangle 27
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 78, 3);

        //triangle 28
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 81, 3);

        //triangle 29
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 84, 3);

        //triangle 30
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 87, 3);

        //triangle 31
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 90, 3);

        //triangle 32
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 93, 3);

        //triangle 32
        glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
        glDrawArrays(GL_TRIANGLES, 96, 3);

            //triangle 33
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 99, 3);

            //triangle 33 (2)
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 87, 3);

            //triangle 34
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 102, 3);

            //triangle 35
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 105, 3);

            //triangle 36
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 108, 3);

            //triangle 37
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 111, 3);

            //triangle 38
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 114, 3);

            //triangle 39
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 117, 3);

            //triangle 40
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 120, 3);

            //triangle 41
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 123, 3);

            //triangle 42
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 126, 3);

            //triangle 43
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 129, 3);

            //triangle 44
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 132, 3);

            //triangle 45
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 135, 3);

            //triangle1 46
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 138, 3);

            //triangle 47
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 141, 3);

            //triangle 48
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 144, 3);

            //triangle 49
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 147, 3);

            //triangle 50
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 150, 3);

            //triangle 51
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 153, 3);

            //triangle 52
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 156, 3);


            //triangle 53
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 159, 3);


            //triangle 54
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 162, 3);

            //triangle 55
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 165, 3);

            //triangle 56
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 168, 3);

            //triangle 57
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 171, 3);

            //triangle 58
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 174, 3);


                //triangle 59
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 177, 3);

            //triangle 60
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 180, 3);

            //triangle 61
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 183, 3);

            //triangle 62
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 186, 3);

            //triangle 63
            glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
            glDrawArrays(GL_TRIANGLES, 189, 3);


                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 192, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 195, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 198, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 201, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 204, 3);

                // well
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 207, 3);


                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 210, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 213, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 216, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 219, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 222, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, .0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 225, 3);

                //triangle 63
                glUniform4f(uColorLocation, 1.0f, .0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 228, 3);

                //triangle front side legs completed
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 231, 3);

                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 234, 3);

                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 237, 3);

                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 240, 3);

                glUniform4f(uColorLocation, 1.0f, .0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 243, 3);

                glUniform4f(uColorLocation, 1.0f, .0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 246, 3);

                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 249, 3);

                glUniform4f(uColorLocation, 1.0f, .0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 252, 3);

                glUniform4f(uColorLocation, 1.0f, .0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 255, 3);

                glUniform4f(uColorLocation, 1.0f, .0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 258, 3);

                glUniform4f(uColorLocation, 1.0f, .0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 261, 3);

                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 264, 3);

                // before head
                glUniform4f(uColorLocation, 1.0f, 1.0f, .0f, .0f);
                glDrawArrays(GL_TRIANGLES, 267, 3);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 270, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 272, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 274, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 276, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 278, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 280, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 282, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 284, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 286, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 288, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 290, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 292, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 294, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 296, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 298, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 300, 2);

                //good
                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 302, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 304, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 306, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 308, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 310, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 312, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 314, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 316, 2);

                glUniform4f(uColorLocation, .0f, .0f, .0f, .0f);
                glDrawArrays(GL_LINES, 318, 2);






        }

}
