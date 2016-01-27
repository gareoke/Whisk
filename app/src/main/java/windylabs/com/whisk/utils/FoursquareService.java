/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package windylabs.com.whisk.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import windylabs.com.whisk.models.foursquare.ResponseHolder;

public final class FoursquareService {
    public interface FoursquareSearch {
        @GET("/v2/venues/search")
        Call<ResponseHolder> response(@Query("client_id") String client_id, @Query("client_secret") String client_secret, @Query("ll") String ll, @Query("v") String version, @Query("query") String query);

        @GET("/v2/venues/search")
        Observable<ResponseHolder> responseRx(@Query("client_id") String client_id, @Query("client_secret") String client_secret, @Query("ll") String ll, @Query("v") String version, @Query("query") String query);
    }
}