package nl.fontys.sebivenlo.statewalkertest;

import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sebivenlo.statewalker.ContextBase;
import static org.assertj.core.api.Assertions.assertThat;
import static nl.fontys.sebivenlo.statewalkertest.S.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ContextBaseTest {

    Context ctx;

    @BeforeEach
    public void setup() {
        ctx = new Context( S.class ).initialize()
                .setDebug( true );
    }

    @Test
    public void constructorWorks() {
        System.out.println( "==== constructorWorks" );
        String ss = ctx.logicalState();
        System.out.println( "initial state = " + ss );
        assertThat( ss ).isEqualTo( "SI" );
    }

    @Test
    public void testE1() {
        System.out.println( "==== e1" );
        ctx.e1();
        assertThat( ctx.logicalState() ).isEqualTo( "S1.S11" );
    }

    @Test
    public void testE2() {
        System.out.println( "==== e2" );
        ctx.e2();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S21" );
    }

    @Test
    public void testE3() {
        System.out.println( "==== e3" );
        ctx.e1();
        ctx.setDebug( false );

        ctx.e3();
        assertThat( ctx.logicalState() ).isEqualTo( "SI" );
    }

    @Test
    public void testE4() {
        Logger ctxLogger = Logger.getLogger( ContextBase.class.getName() );
        MyHandler mh = new MyHandler();
        ctxLogger.addHandler( mh );
        ctxLogger.setLevel( Level.FINE );
        System.out.println( "==== e4" );
        ctx.e1();
        assertThat( ctx.isDebug() ).isTrue();
        mh.flush();
        ctx.e4();
        assertThat( ctx.logicalState() ).isEqualTo( "S1.S12" );
//        assertThat(" "+mh.lastMessage,).isTrue();
        assertThat( mh.lastMessage ).as( "wrong message" ).contains( "from" );
        assertThat( mh.lastParams ).as( "state not found" ).contains( "S1.S11" );
        ctx.setDebug( false );
        assertThat( ctx.isDebug() ).isFalse();
        ctx.e4();
        assertThat( ctx.logicalState() ).isEqualTo( "S1.S12" );
    }

    @Test
    public void testE5() {
        System.out.println( "==== e5" );
        ctx.e2();

        ctx.e5();
        assertThat( ctx.logicalState() ).isEqualTo( "S1.S11" );
    }

    @Test
    public void testE6() {
        System.out.println( "==== e6" );
        ctx.e2();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S21" );

        ctx.e6();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S22.S221" );

        ctx.e6();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S21" );
    }

    @Test
    public void testE7() {
        System.out.println( "==== e7" );
        ctx.e2();
        ctx.e6();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S22.S221" );

        ctx.e7();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S22.S222" );

    }

    @Test
    public void testE8() {
        System.out.println( "==== e8" );
        assertThat( ctx.logicalState() ).isEqualTo( "SI" );
        ctx.e8();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S32.S321" );
    }

    @Test
    public void testE11a() {
        System.out.println( "==== e11a" );
        ctx.e8();
        ctx.e10();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S33.S331" );
        ctx.e11();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S33.S332" );
        ctx.e11();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S33.S331" );

    }

    @Test
    public void testHistory() {
        System.out.println( "==== history" );
        ctx.e2();
        ctx.e6();
        ctx.e7();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S22.S222" );
        ctx.e6();
        ctx.e6();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S22.S222" );
    }

    @Test
    public void testS3() {
        System.out.println( "S3========" );
        ctx.e1();
        assertThat( ctx.logicalState() ).isEqualTo( "S1.S11" );
        ctx.e9();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S31" );
        ctx.e10();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S32.S321" );
        ctx.e12();
        assertThat( ctx.logicalState() ).isEqualTo( "S1.S11" );

        ctx.e9();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S32.S321" );

        ctx.e10();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S33.S331" );
        ctx.e11();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S33.S332" );
        ctx.e12();
        assertThat( ctx.logicalState() ).isEqualTo( "S1.S11" );
        ctx.e9();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S33.S332" );
        ctx.e10();
        ctx.e10();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S32.S321" );
        ctx.e12();
        ctx.e9();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S32.S321" );

    }

    @Test
    public void testDeepHistory() {
        System.out.println( "Deep history" );
        ctx.e8();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S32.S321" );
        ctx.e11();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S32.S322" );
        ctx.e12();
        ctx.e9();
        assertThat( ctx.logicalState() ).isEqualTo( "S3.S32.S322" );
    }

    @Test
    public void testE13() {
        ctx.e2();
        ctx.e6();
        ctx.e7();
        assertThat( ctx.logicalState() ).isEqualTo( "S2.S22.S222" );
        ctx.e13();
        assertThat( ctx.logicalState() ).isEqualTo( "S1.S11" );

    }

    @Test
    public void leavNonExistingSubStates() {
        assertThatThrownBy( () -> {
            ctx.e1();
            ctx.leaveSubStates( S21 );
        } ).isInstanceOf( IllegalArgumentException.class );
    }

}
