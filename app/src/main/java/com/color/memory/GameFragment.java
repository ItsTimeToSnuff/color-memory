package com.color.memory;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GameFragment extends Fragment implements View.OnClickListener {

    public static final String COUNTER_KEY = "COUNTER";

    private AtomicInteger gameLvl;
    private TextView question;
    private List<Color> colors;
    private TextView score;
    private Random random;
    private AtomicInteger counter;
    private View view;
    private Bundle bundle;
    private Resources res;
    private List<Color> tmp;
    private AtomicInteger answerCounter;
    private boolean isGameRunning;
    private Color green;
    private Color red;
    private Color blue;
    private Color yellow;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_fragment, container, false);
        isGameRunning = true;
        gameLvl = new AtomicInteger(1);
        answerCounter = new AtomicInteger(1);
        question = view.findViewById(R.id.question);
        score = view.findViewById(R.id.score);
        colors = initColors();
        initAnswers();
        bundle = getArguments();
        res = getResources();
        counter = new AtomicInteger(0);
        random = new Random();
        tmp = new ArrayList<>();
        score = view.findViewById(R.id.score);
        bundle = getArguments();
        game();
        return view;
    }

    @Override
    public void onClick(View v) {
        Button receivedAnswer = view.findViewById(v.getId());
        if (answerCounter.getAndIncrement() <= tmp.size()) {
            if (receivedAnswer.getText().equals(tmp.get(answerCounter.get() - 2).getName())) {
                score.setText(String.format(res.getString(R.string.score_text), counter.incrementAndGet() * 10));
            } else {
                gameLvl.set(1);
                answerCounter.set(1);
                tmp = new ArrayList<>();
                endGame();
            }
            if (answerCounter.get() - 1 == tmp.size()) {
                gameLvl.incrementAndGet();
                answerCounter.set(1);
                game();
            }
        }

    }

    private void game() {
        score.setText(String.format(res.getString(R.string.score_text), counter.get() * 10));
        bundle.putInt(COUNTER_KEY, counter.get() * 10);
        tmp.add(randomizeColor());
        Handler handler = new Handler();
        final int[] tapCount = {0};
        final ListIterator<Color> colorListIterator = tmp.listIterator();
        for (; tapCount[0] < gameLvl.get(); tapCount[0]++) {
            if (colorListIterator.hasNext()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isGameRunning) {
                            int background = colorListIterator.next().getValue();
                            question.setBackgroundColor(background);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    question.setBackgroundColor(0xffffffff);
                                }
                            }, 500);
                        }
                    }
                }, 1000 * tapCount[0]);
            }
        }
    }


    private List<Color> initColors() {
        colors = new ArrayList<>();
        green = new Color("GREEN", 0xFF008000);
        colors.add(green);
        red = new Color("RED", 0xFFFF0000);
        colors.add(red);
        blue = new Color("BLUE", 0xFF0000FF);
        colors.add(blue);
        yellow = new Color("YELLOW", 0xFFFFEB3B);
        colors.add(yellow);
        return colors;
    }

    private void initAnswers() {
        Button answer1 = view.findViewById(R.id.answer_1);
        answer1.setText(green.getName());
        answer1.setBackgroundColor(green.getValue());
        answer1.setOnClickListener(this);
        Button answer2 = view.findViewById(R.id.answer_2);
        answer2.setText(red.getName());
        answer2.setBackgroundColor(red.getValue());
        answer2.setOnClickListener(this);
        Button answer3 = view.findViewById(R.id.answer_3);
        answer3.setText(blue.getName());
        answer3.setBackgroundColor(blue.getValue());
        answer3.setOnClickListener(this);
        Button answer4 = view.findViewById(R.id.answer_4);
        answer4.setText(yellow.getName());
        answer4.setBackgroundColor(yellow.getValue());
        answer4.setOnClickListener(this);
    }

    private Color randomizeColor() {
        int randomIdx = random.nextInt(4);
        return colors.get(randomIdx);
    }

    private void endGame() {
        isGameRunning = false;
        ((MainActivity) getActivity()).loadFragment(new EndGameFragment());
    }
}
