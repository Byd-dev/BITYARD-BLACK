package com.ltqh.qh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseActivity;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.SearchFragment;
import com.ltqh.qh.fragment.find.QuestionFragment;
import com.ltqh.qh.fragment.forum.ForumFragment;
import com.ltqh.qh.fragment.forum.GuliaoDetailFragment;
import com.ltqh.qh.fragment.market.IntroduceFragment;
import com.ltqh.qh.fragment.market.LearnFragment;
import com.ltqh.qh.fragment.market.SkillAllFragment;
import com.ltqh.qh.fragment.market.SkillFragment;
import com.ltqh.qh.fragment.market.StockForeignSelectFragment;
import com.ltqh.qh.fragment.market.StockFragment;
import com.ltqh.qh.fragment.market.StockSelectFragment;
import com.ltqh.qh.fragment.market.StockToolFragment;
import com.ltqh.qh.fragment.market.ToolFragment;
import com.ltqh.qh.fragment.news.AudioVideoFragment;
import com.ltqh.qh.fragment.news.BlockFragment;
import com.ltqh.qh.fragment.news.BtcFragment;
import com.ltqh.qh.fragment.news.BtcNewsFragment;
import com.ltqh.qh.fragment.news.FinancialCalendarFragment;
import com.ltqh.qh.fragment.news.InfoFragment;
import com.ltqh.qh.fragment.news.LearnClassFragment;
import com.ltqh.qh.fragment.news.LiveFragment;
import com.ltqh.qh.fragment.news.StrategyFragment;
import com.ltqh.qh.fragment.news.TypeFragment;
import com.ltqh.qh.fragment.news.VideoFragment;
import com.ltqh.qh.fragment.user.FeedbackFragment;
import com.ltqh.qh.fragment.user.ForgetFragment;
import com.ltqh.qh.fragment.user.LoginFragment;
import com.ltqh.qh.fragment.user.MyMeaasgeFragment;
import com.ltqh.qh.fragment.user.NicknameFragment;
import com.ltqh.qh.fragment.user.PersonalCenterFragment;
import com.ltqh.qh.fragment.user.RegisterFragment;
import com.ltqh.qh.fragment.user.ResetPassFragment;
import com.ltqh.qh.fragment.user.SignFragment;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.operation.fragment.home.OSearchFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class IntentActivity extends OBaseActivity implements View.OnClickListener {


    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.text_title)
    TextView text_title;

    @BindView(R.id.layout_bar)
    RelativeLayout layout_bar;

    @BindView(R.id.stay_line)
    View stay_line;

    private int isLogin = 0;

    private static final String TYPE = "LOGIN_TYPE";

    private static final String SORT = "SORT";
    private static final String TITLE = "TITLE";


    private int type;
    private StrategyFragment strategyFragment;
    private StockFragment stockFragment;
    private StockSelectFragment stockSelectFragment;
    private ToolFragment toolFragment;
    private LearnFragment learnFragment;
    private IntroduceFragment introduceFragment;
    private SkillFragment skillFragment;
    private StockToolFragment stockToolFragment;
    private LearnClassFragment learnClassFragment;
    private SkillAllFragment skillAllFragment;
    private ForumFragment forumFragment;
    private GuliaoDetailFragment guliaoDetailFragment;
    private StockForeignSelectFragment stockForeignSelectFragment;
    private InfoFragment infoFragment;
    private AudioVideoFragment audioVideoFragment;
    private VideoFragment videoFragment;
    private BtcFragment btcFragment;
    private BlockFragment blockFragment;
    private TypeFragment typeFragment;
    private FeedbackFragment feedbackFragment;
    private QuestionFragment questionFragment;
    private FragmentTransaction ft;
    private SearchFragment searchFragment;
    private FinancialCalendarFragment financialCalendarFragment;
    private BtcNewsFragment btcNewsFragment;//BTC介绍

    public static void enter(Context context, int type) {
        Intent intent = new Intent(context, IntentActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    public static void enter(Context context, int type, int id) {
        Intent intent = new Intent(context, IntentActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(Constant.PARAM_ID, id);
        context.startActivity(intent);
    }

    public static void enter(Context context, int type, String sort, String title) {
        Intent intent = new Intent(context, IntentActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(SORT, sort);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBar(getResources().getColor(R.color.maincolor));

        type = getIntent().getIntExtra(TYPE, 0);
        int id = getIntent().getIntExtra(Constant.PARAM_ID, 0);


        String sort = getIntent().getStringExtra(SORT);
        String title = getIntent().getStringExtra(TITLE);

        if (type == Constant.STRATEGY) {
            addStrategyFragment();
            text_title.setText(getResources().getString(R.string.text_strategy));
        } else if (type == Constant.STOCKMARKET) {
            addStockFragment();
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);

        } else if (type == Constant.TOOL) {
            addToolFragment();
            text_title.setText(getResources().getString(R.string.text_conversion));
        } else if (type == Constant.LEARN) {
            addLearnFragment();
            text_title.setText(getResources().getString(R.string.text_base_knowledge));
        } else if (type == Constant.INTRODUCE) {
            addIntroduceFragment();
            text_title.setText(getResources().getString(R.string.text_type_indruction));

        } else if (type == Constant.SKILL) {
            addSkillFragment();
            text_title.setText(getResources().getString(R.string.text_skill));

        } else if (type == Constant.STOCKTOOL) {
            addStockToolFragment();
            text_title.setText(getResources().getString(R.string.text_gain_losses));
        } else if (type == Constant.LEARNCLASS) {
            addLearnClassFragment();
            text_title.setText(getResources().getString(R.string.text_class));

        } else if (type == Constant.SKILLALL) {
            addSkillAllFragment();
            text_title.setText(getResources().getString(R.string.text_conversion));
        } else if (type == Constant.FORUM) {
            addForumFragment();
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
        } else if (type == Constant.FORUM_PUBLISH) {
            text_title.setText(getResources().getString(R.string.text_detail));
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            addGuliaoDetailFragment(id);
        } else if (type == Constant.STOCKSLIDE) {
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            text_title.setText(title);
            addStockItemFragment(sort, title);
        } else if (type == Constant.STOCKFOREIGNSLIDE) {
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            text_title.setText(title);
            addStockForeignItemFragment(sort, title);
        } else if (type == Constant.INFO) {
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            addInfoFragment();
        } else if (type == Constant.AUDIOVIDEO) {
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            addAudioVideoFragment();
        } else if (type == Constant.VIDEO) {
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            addVideoFragment();
        } else if (type == Constant.BTC) {
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            addBtcFragment();
        } else if (type == Constant.BLOCK) {
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            addBlockFragment();
        } else if (type == Constant.TYPE) {
            text_title.setText(getResources().getString(R.string.text_alltype));
            addTypeFragment();
        } else if (type == Constant.FEEDBACK) {
            text_title.setText(getResources().getString(R.string.text_back));
            addFeedbackFragment();
        }else if (type == Constant.QUESTION) {
            //text_title.setText("趣味答题");
            layout_bar.setVisibility(View.GONE);
            stay_line.setVisibility(View.GONE);
            addQuestionFragment();
        }else if (type==Constant.SEARCH){
            layout_bar.setVisibility(View.GONE);
            addSearchFragment();
        }else if (type==Constant.CALENDER){
            addCalenderFragment();
        }

    }

    private void addBtcNewsFragment() {
        String name = BtcNewsFragment.class.getSimpleName();
        btcNewsFragment = new BtcNewsFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, btcNewsFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addCalenderFragment() {
        String name = FinancialCalendarFragment.class.getSimpleName();
        financialCalendarFragment = new FinancialCalendarFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, financialCalendarFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }


    private void addSearchFragment() {
        String name = SearchFragment.class.getSimpleName();
        searchFragment = new SearchFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, searchFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_intent;
    }


    private void addStrategyFragment() {
        String name = StrategyFragment.class.getSimpleName();
        strategyFragment = new StrategyFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, strategyFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addStockFragment() {
        String name = StockFragment.class.getSimpleName();
        stockFragment = new StockFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, stockFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addStockItemFragment(String sort, String title) {
        String name = StockSelectFragment.class.getSimpleName();
        stockSelectFragment = new StockSelectFragment(sort, title);
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, stockSelectFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addStockForeignItemFragment(String sort, String title) {
        String name = StockForeignSelectFragment.class.getSimpleName();
        stockForeignSelectFragment = new StockForeignSelectFragment(sort, title);
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, stockForeignSelectFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }


    private void addToolFragment() {
        String name = ToolFragment.class.getSimpleName();
        toolFragment = new ToolFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, toolFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addLearnFragment() {
        String name = LearnFragment.class.getSimpleName();
        learnFragment = new LearnFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, learnFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addIntroduceFragment() {
        String name = IntroduceFragment.class.getSimpleName();
        introduceFragment = new IntroduceFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, introduceFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addSkillFragment() {
        String name = SkillFragment.class.getSimpleName();
        skillFragment = new SkillFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, skillFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addStockToolFragment() {
        String name = StockToolFragment.class.getSimpleName();
        stockToolFragment = new StockToolFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, stockToolFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addLearnClassFragment() {
        String name = LearnClassFragment.class.getSimpleName();
        learnClassFragment = new LearnClassFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, learnClassFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addSkillAllFragment() {
        String name = SkillAllFragment.class.getSimpleName();
        skillAllFragment = new SkillAllFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, skillAllFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addForumFragment() {
        String name = ForumFragment.class.getSimpleName();
        forumFragment = new ForumFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, forumFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addGuliaoDetailFragment(int id) {
        String name = GuliaoDetailFragment.class.getSimpleName();
        guliaoDetailFragment = new GuliaoDetailFragment(id);
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, guliaoDetailFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addInfoFragment() {
        String name = InfoFragment.class.getSimpleName();
        infoFragment = new InfoFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, infoFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addAudioVideoFragment() {
        String name = AudioVideoFragment.class.getSimpleName();
        audioVideoFragment = new AudioVideoFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, audioVideoFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addVideoFragment() {
        String name = VideoFragment.class.getSimpleName();
        videoFragment = new VideoFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, videoFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addBtcFragment() {
        String name = BtcFragment.class.getSimpleName();
        btcFragment = new BtcFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, btcFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addBlockFragment() {
        String name = BlockFragment.class.getSimpleName();
        blockFragment = new BlockFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, blockFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addTypeFragment() {
        String name = TypeFragment.class.getSimpleName();
        typeFragment = new TypeFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, typeFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addFeedbackFragment() {
        String name = FeedbackFragment.class.getSimpleName();
        feedbackFragment = new FeedbackFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, feedbackFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addQuestionFragment() {
        String name = QuestionFragment.class.getSimpleName();
        questionFragment = new QuestionFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, questionFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        img_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.img_back:
                finish();
               // EventBus.getDefault().postSticky(Constant.ONRESUME_TYPE);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
