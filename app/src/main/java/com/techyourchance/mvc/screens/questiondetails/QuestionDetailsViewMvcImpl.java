package com.techyourchance.mvc.screens.questiondetails;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.BaseViewMvc;

public class QuestionDetailsViewMvcImpl extends BaseViewMvc implements QuestionDetailsViewMvc {

    private ProgressBar m_progressBar = null;
    private TextView m_tvTitle = null;
    private TextView m_tvBody = null;

    public QuestionDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_question_details, parent, false));

        m_progressBar = findViewById(R.id.progress);
        m_tvTitle = findViewById(R.id.txt_question_title);
        m_tvBody = findViewById(R.id.txt_question_body);

    }

    @Override
    public void bindQuestion(QuestionDetails questionDetails) {
        m_tvTitle.setText(Html.fromHtml(questionDetails.getTitle()));
        m_tvBody.setText(Html.fromHtml(questionDetails.getBody()));
    }

    @Override
    public void showProgressBar() {
        m_progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        m_progressBar.setVisibility(View.GONE);
    }
}
