console.log("Travel log scripts loaded")

document.addEventListener('DOMContentLoaded', function () {
    const wishlistToggleBtn = document.getElementById('wishlistToggleBtn');
    const wishlistPopover = document.getElementById('wishlistPopover');

    if (wishlistToggleBtn && wishlistPopover) {
        wishlistToggleBtn.addEventListener('click', function (e) {
            e.preventDefault();
            wishlistPopover.style.display = wishlistPopover.style.display === 'none' ? 'block' : 'none';
        });

        document.addEventListener('click', function (e) {
            if (!wishlistPopover.contains(e.target) && !wishlistToggleBtn.contains(e.target)) {
                wishlistPopover.style.display = 'none';
            }
        });
    }

    // Wishlist
    const wishlistForm = document.getElementById('wishlistForm');
    if (wishlistForm) {
        wishlistForm.addEventListener('submit', function (e) {
            e.preventDefault();

            const startDate = this.querySelector('input[name="travel_date"]').value;
            const endDate = this.querySelector('input[name="end_date"]').value;

            if (!startDate || !endDate) {
                alert('여행 시작일과 종료일을 모두 입력해주세요.');
                return;
            }

            const startDateObj = new Date(startDate);
            const endDateObj = new Date(endDate);

            if (startDateObj > endDateObj) {
                alert('여행 시작일은 종료일보다 이전이어야 합니다.');
                return;
            }

            const today = new Date();
            today.setHours(0, 0, 0, 0);
            startDateObj.setHours(0, 0, 0, 0);

            if (startDateObj < today) {
                alert('여행 시작일은 오늘 이후여야 합니다.');
                return;
            }

            const countryId = this.querySelector('input[name="country_id"]').value;

            if (!countryId) {
                alert('국가 정보가 없습니다.');
                return;
            }

            const currentUserId = 1;

            fetch(`/users/${currentUserId}/wishlists`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    countryId: parseInt(countryId),
                    travelDate: startDate,
                    endDate: endDate
                })
            })
                .then(res => {
                    console.log('Response status:', res.status);
                    if (!res.ok) {
                        return res.json().then(data => {
                            console.log('Error response data:', data);
                            if (res.status === 409) {
                                throw new Error('이미 동일한 날짜로 등록된 위시리스트가 있습니다.');
                            }
                            throw new Error(data.message || '위시리스트 등록 중 오류가 발생했습니다.');
                        });
                    }
                    return res.json();
                })
                .then(data => {
                    wishlistToggleBtn.textContent = 'Wished';
                    wishlistPopover.style.display = 'none';
                    alert('위시리스트에 추가되었습니다.');
                })
                .catch(err => {
                    console.error('Error details:', err);
                    alert(err.message);
                });
        });
    }

    // Comment
    const commentBox = document.querySelector('.comment-box');
    if (commentBox) {
        const logId = commentBox.dataset.logId;
        // const token = localStorage.getItem('token');

        // creation
        const commentForm = document.querySelector('.comment-form');
        if (commentForm) {
            commentForm.addEventListener('submit', function (e) {
                e.preventDefault();
                const content = this.querySelector('textarea').value.trim();
                if (content) {
                    fetch(`/gallery/${logId}/comments`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                            // 'Authorization': 'Bearer ' + token
                        },
                        body: JSON.stringify({
                            content: content,



                            userId: 1





                        })
                    })
                        .then(res => {
                            if (!res.ok) throw new Error('Comment creation failed');
                            return res.json();
                        })
                        .then(data => {

                            // Add
                            const commentList = document.querySelector('.comment-box');
                            const newComment = document.createElement('div');
                            newComment.className = 'comment';
                            newComment.innerHTML = `
                                <p data-comment-id="${data.comment_id}">
                                    <strong>${data.user_nickname}</strong>:
                                    <span class="comment-content">${data.content}</span>
                                    <button class="edit-btn">수정</button>
                                    <button class="delete-btn">삭제</button>
                                    <span class="edit-form" style="display:none;">
                                        <input type="text" class="edit-input" value="${data.content}" />
                                        <button class="save-btn">Save</button>
                                        <button class="cancel-btn">Cancel</button>
                                    </span>
                                </p>
                            `;
                            commentList.appendChild(newComment);

                            // Clear
                            this.querySelector('textarea').value = '';
                        })
                        .catch(err => {
                            console.error(err);
                            alert('댓글 등록에 실패했습니다.');
                        });
                }
            });
        }





        commentBox.addEventListener('click', function (e) {
            const target = e.target;
            const commentElement = target.closest('p');
            if (!commentElement) return;

            const commentId = commentElement.dataset.commentId;
            const editBtn = commentElement.querySelector('.edit-btn');
            const deleteBtn = commentElement.querySelector('.delete-btn');
            const saveBtn = commentElement.querySelector('.save-btn');
            const cancelBtn = commentElement.querySelector('.cancel-btn');
            const editForm = commentElement.querySelector('.edit-form');
            const commentContent = commentElement.querySelector('.comment-content');
            const editInput = commentElement.querySelector('.edit-input');

            if (target === editBtn) {
                editForm.style.display = 'inline';
                commentContent.style.display = 'none';
                editBtn.style.display = 'none';
                deleteBtn.style.display = 'none';
            } else if (target === cancelBtn) {
                editForm.style.display = 'none';
                commentContent.style.display = 'inline';
                editBtn.style.display = 'inline';
                deleteBtn.style.display = 'inline';
            } else if (target === saveBtn) {
                const newContent = editInput.value.trim();
                if (!newContent) return;

                fetch(`/gallery/${logId}/comments/${commentId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                        // 'Authorization': 'Bearer ' + token
                    },
                    body: JSON.stringify({content: newContent})
                })
                    .then(res => {
                        if (!res.ok) throw new Error('Update failed');
                        return res.json();
                    })
                    .then(data => {
                        commentContent.textContent = data.content;
                        editForm.style.display = 'none';
                        commentContent.style.display = 'inline';
                        editBtn.style.display = 'inline';
                        deleteBtn.style.display = 'inline';
                    })
                    .catch(err => {
                        console.error(err);
                        alert('댓글 수정에 실패했습니다.');
                    });
            } else if (target === deleteBtn) {
                if (confirm('댓글을 삭제하시겠습니까?')) {
                    fetch(`/gallery/${logId}/comments/${commentId}`, {
                        method: 'DELETE'
                        // headers: {
                        //     'Authorization': 'Bearer ' + token
                        // }
                    })
                        .then(res => {
                            if (!res.ok) throw new Error('Delete failed');
                            commentElement.closest('.comment').remove();
                        })
                        .catch(err => {
                            console.error(err);
                            alert('댓글 삭제에 실패했습니다.');
                        });
                }
            }
        });
    }

    // Like
    const likeBtn = document.querySelector('.like-btn');
    if (likeBtn) {
        likeBtn.addEventListener('click', function () {
            const logId = this.dataset.logId;
            const button = this;
            const likeCount = document.querySelector('.like-count');

            fetch(`/gallery/${logId}/likes`, {
                method: 'POST'
                // headers: {
                //     'Authorization': 'Bearer ' + localStorage.getItem('token')
                // }
            })
                .then(response => {
                    console.log('Response status:', response.status);
                    return response.json();
                })
                .then(data => {
                    console.log('Server response data:', data);
                    console.log('liked value:', data.liked);
                    console.log('likeCount value:', data.likeCount);
                    button.textContent = data.liked ? '💔 Unlike' : '❤️ Like';
                    button.classList.toggle('liked', data.liked);
                    likeCount.textContent = '❤️ ' + data.likeCount + ' Likes';
                })
                .catch(error => {
                    console.error('Error:', error);
                    button.textContent = button.classList.contains('liked') ? '💔 Unlike' : '❤️ Like';
                });
        });
    }
});