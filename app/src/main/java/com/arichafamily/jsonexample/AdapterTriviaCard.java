package com.arichafamily.jsonexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class AdapterTriviaCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<TriviaCard> data = Collections.emptyList();

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterTriviaCard(Context context, List<TriviaCard> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_trivia_card, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        TriviaCard current = data.get(position);
        myHolder.tvQuestion.setText(current.question);
        myHolder.tvAnswerA.setText(current.answerA);
        myHolder.tvAnswerB.setText(current.answerB);
        myHolder.tvAnswerC.setText(current.answerC);
        myHolder.tvAnswerD.setText(current.answerD);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView tvQuestion;
        TextView tvAnswerA;
        TextView tvAnswerB;
        TextView tvAnswerC;
        TextView tvAnswerD;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            tvAnswerA = (TextView) itemView.findViewById(R.id.tvAnswerA);
            tvAnswerB = (TextView) itemView.findViewById(R.id.tvAnswerB);
            tvAnswerC = (TextView) itemView.findViewById(R.id.tvAnswerC);
            tvAnswerD = (TextView) itemView.findViewById(R.id.tvAnswerD);
        }

    }

}
