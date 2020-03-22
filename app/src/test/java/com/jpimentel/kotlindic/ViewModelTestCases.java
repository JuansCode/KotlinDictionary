package com.jpimentel.kotlindic;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.jpimentel.kotlindic.model.UrbanResponse;
import com.jpimentel.kotlindic.network.ApiClient;
import com.jpimentel.kotlindic.util.RxSingleSchedulers;
import com.jpimentel.kotlindic.viewmodel.urbanlist.UrbanViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Single;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ViewModelTestCases {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Mock
    Context context;
    @Mock
    ApiClient apiClient;
    @Mock
    Observer<Boolean> observer;
    @Mock
    LifecycleOwner lifecycleOwner;
    private Lifecycle lifecycle;
    private UrbanViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewModel = new UrbanViewModel(apiClient, RxSingleSchedulers.Companion.getTEST_SCHEDULER(), context);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        viewModel.getDataLoading().observeForever(observer);
    }

    @Test
    public void testNull() {

        when(apiClient.getDefinitionList(anyString())).thenReturn(null);
        Assert.assertNotNull(viewModel.getData());
        assertTrue(viewModel.getDataLoading().hasObservers());

    }

    @Test
    public void testApiFetchDataSuccess() {
        // Mock API response
        UrbanResponse urbanResponse = new UrbanResponse();
        ArrayList<UrbanResponse> list = new ArrayList<>();
        list.add(urbanResponse);
        when(apiClient.getDefinitionList(anyString())).thenReturn(Single.just(urbanResponse));
        viewModel.loadData("Banana");
        verify(observer).onChanged(true);
//        verify(observer).onChanged(false);
//        assertFalse(viewModel.getData().isEmpty());
    }



    @Test
    public void testApiFetchDataError() {
        when(apiClient.getDefinitionList(anyString())).thenReturn(Single.error(new Throwable("Api error")));
        viewModel.loadData("");
        assertTrue(viewModel.getData().isEmpty());
        assertFalse(viewModel.getErrorMessage().isEmpty());

    }

    @After
    public void tearDown() throws Exception {
        apiClient = null;
        viewModel = null;
    }
}
