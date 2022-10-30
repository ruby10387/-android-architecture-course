package com.techyourchance.mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.networking.QuestionDetailsResponseSchema;
import com.techyourchance.mvc.networking.QuestionSchema;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailsActivity extends BaseActivity {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    private StackoverflowApi m_StackoverflowApi = null;
    private QuestionDetailsViewMvc m_ViewMvc = null;

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_ViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(null);
        m_StackoverflowApi = getCompositionRoot().getStackoverflowApi();
        setContentView(m_ViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        m_ViewMvc.showProgressBar();
        fetchQuestionDetails();
    }

    private void fetchQuestionDetails() {
        m_StackoverflowApi.fetchQuestionDetails(getQuestionId())
                .enqueue(new Callback<QuestionDetailsResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionDetailsResponseSchema> call, Response<QuestionDetailsResponseSchema> response) {
                        if (response.isSuccessful()) {
                            bindQuestionDetails(response.body().getQuestion());
                        } else {
                            networkCallFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionDetailsResponseSchema> call, Throwable throwable) {
                        networkCallFailed();
                    }
                });
    }

    private String getQuestionId() {
        return getIntent().getStringExtra(EXTRA_QUESTION_ID);
    }

    private void bindQuestionDetails(QuestionSchema questionSchema) {
        m_ViewMvc.hideProgressBar();
        QuestionDetails questionDetails = new QuestionDetails(questionSchema.getId(), questionSchema.getTitle(), questionSchema.getBody());
        m_ViewMvc.bindQuestion(questionDetails);
    }

    private void networkCallFailed() {
        m_ViewMvc.hideProgressBar();
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }
}
