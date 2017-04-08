/**
 * Copyright 2017 Palantir Technologies
 *
 * Licensed under the BSD-3 License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.palantir.atlasdb.timelock.paxos;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.palantir.lock.remoting.BlockingTimeoutException;

public class BlockingTimeoutExceptionMapper implements ExceptionMapper<BlockingTimeoutException> {
    @Override
    public Response toResponse(BlockingTimeoutException exception) {
        return Response.serverError()
                .status(503)
                .entity(exception)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}