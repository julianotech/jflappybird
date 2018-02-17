package uk.jtech.game.jflappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static uk.jtech.game.jflappybird.Constants.birdinix;
import static uk.jtech.game.jflappybird.Constants.gap;
import static uk.jtech.game.jflappybird.Constants.pipesTime;
import static uk.jtech.game.jflappybird.Constants.posMax;
import static uk.jtech.game.jflappybird.Constants.screenx;
import static uk.jtech.game.jflappybird.Constants.screeny;

public class MainClass extends ApplicationAdapter {

    private SpriteBatch batch;

    private Background background;

    private Bird bird;

    private List<Pipe> pipes;

    private float pipetime;


    @Override
    public void create() {
        batch = new SpriteBatch();

        background = new Background();

        bird = new Bird( birdinix, screeny / 2 );

        pipes = new ArrayList<Pipe>();

        pipetime = pipesTime;

    }

    @Override
    public void render() {
        input();

        update( Gdx.graphics.getDeltaTime() );

        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        batch.begin();
        draw();
        batch.end();
    }

    private void draw() {
        background.draw( batch );

        for (Pipe p : pipes) {
            p.draw( batch );
        }

        bird.draw( batch );
    }

    private void update(float time) {
        background.update( time );

        for (int i = 0; i < pipes.size(); i++) {
            if (pipes.get( i ).update( time ) == 1) {
                pipes.remove( i );
                i--;
            }
        }

        pipetime -= time;
        if (pipetime <= 0) {
            Random random = new Random();
            int pos = random.nextInt( posMax );
            pos -= posMax / 2;
            pipes.add( new Pipe( screenx, screeny / 2 + pos + gap / 2, true ) );
            pipes.add( new Pipe( screenx, screeny / 2 + pos - gap / 2, false ) );
            pipetime = pipesTime;
        }

        bird.update( time );
    }

    private void input() {
        if (Gdx.input.justTouched()) {
            bird.impulse();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();

        background.dispose();

        bird.dispose();

        for (Pipe p : pipes) {
            p.dispose();
        }

    }
}
