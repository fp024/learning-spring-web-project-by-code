package org.fp024.util;

import org.springframework.http.converter.json.GsonHttpMessageConverter;

public class CustomGsonMessageConvertor extends GsonHttpMessageConverter {
  CustomGsonMessageConvertor() {
    super(GsonHelper.gson());
  }
}
