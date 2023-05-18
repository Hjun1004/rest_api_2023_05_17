package com.ll.rest.base.rsData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RsData<T> {
    private String resultCode;
    private String msg;
    private T data;

    public static <T> RsData<T> of(String resultCode, String msg, T data){
        return new RsData<T>(resultCode, msg, data);
    }

    public static <T> RsData<T> of(String resultCode, String msg){
        return of(resultCode, msg, null);
    }

    //@JsonIgnore
    public boolean isSuccess(){
        return resultCode.startsWith("S-");
    }

    //@JsonIgnore
    public boolean isFail(){
        return !isSuccess();
    }
    // 자바 객체를 브라우저로 던지려면 문자열화가 되어야 하는데 여기에는 규격이있다.
    // JSON이라는 방식을 따른다.
    // 이렇게 만들어 주는데에는 잭슨 라이브러리가 보이지 않지만 사용되고 있다.
    // 리턴 타입이 boolean이고 is또는 get으로 시작하고 인자가 없는 메서드는 실행되서 결과가 JSON형태로 리턴 된다.
    //
}
