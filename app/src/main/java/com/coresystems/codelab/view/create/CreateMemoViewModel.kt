package com.coresystems.codelab.view.create

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.annotation.NonNull
import com.coresystems.codelab.R
import com.coresystems.codelab.model.EMPTY_STRING
import com.coresystems.codelab.model.Memo
import com.coresystems.codelab.repository.Repository
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * ViewModel for matching CreateMemo view. Handles user interactions.
 *
 * Created by coresystems on 23.09.17.
 */
class CreateMemoViewModel : ViewModel() {
    private var memo = Memo()

    /**
     * Saves the memo in it's current state.
     */
    fun saveMemo() {
        bg {
            Repository.instance.saveMemo(memo)
        }
    }

    /**
     * Call this method to update the memo. This is usually needed when the user changed his input.
     */
    fun updateMemo(title: String, description: String) {
        memo = Memo(title = title, description = description)
    }

    /**
     * @return true if the title and content are not blank; false otherwise.
     */
    fun isMemoValid(): Boolean = memo.title.isNotBlank() && memo.description.isNotBlank()

    /**
     * @return an error message if the memo text is blank; an empty string otherwise.
     */
    @NonNull
    fun getTextError(@NonNull context: Context): String = if (memo.description.isBlank()) context.getString(R.string.memo_text_empty_error) else EMPTY_STRING

    /**
     * @return an error message if the memo title is blank; an empty string otherwise.
     */
    @NonNull
    fun getTitleError(@NonNull context: Context): String = if (memo.title.isBlank()) context.getString(R.string.memo_title_empty_error) else EMPTY_STRING
}