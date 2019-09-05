package com.lsy.lib_net.response;


import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_base.exception.ApiException;
import com.lsy.lib_base.exception.CustomException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ResponseTransformer {

    public static <T> ObservableTransformer<ResponseData<T>, Optional<T>> handleResult() {
        return new ObservableTransformer<ResponseData<T>, Optional<T>>() {
            @Override
            public ObservableSource<Optional<T>> apply(Observable<ResponseData<T>> upstream) {
                return upstream.onErrorResumeNext(new ErrorResumeFunction<T>())
                        .flatMap(new ResponseFunction<T>());
            }
        };
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends ResponseData<T>>> {

        @Override
        public ObservableSource<? extends ResponseData<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<ResponseData<T>, ObservableSource<Optional<T>>> {

        @Override
        public ObservableSource<Optional<T>> apply(ResponseData<T> tResponse) throws Exception {
            int code = tResponse.getErrorCode();
            String message = tResponse.getErrorMsg();
            //0成功  -1失败
            if (code >= 0) {
                return Observable.just(tResponse.getData());
            } else {
                return Observable.error(new ApiException(code, message));
            }
        }
    }
}
