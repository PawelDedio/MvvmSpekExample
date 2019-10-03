package com.dedio.domain.use_cases

import com.dedio.domain.models.BaseResult

abstract class BaseUseCase<Response : Any, in Params> {
    abstract suspend fun execute(params: Params): BaseResult<Response>
}