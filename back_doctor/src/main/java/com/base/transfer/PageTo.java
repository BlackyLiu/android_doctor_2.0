package com.base.transfer;

import lombok.Data;

@Data
public class PageTo<T>{

    private  Integer page;

    private  Integer size;

    private  T obj;

}
