console.log("Travel log scripts loaded")

document.addEventListener('DOMContentLoaded', function () {
    // Lightbox
    initializeLightbox();
    
    // Wishlist
    initializeWishlist();
    
    // Comments
    initializeComments();
    
    // Like
    initializeLikes();

    // To latest comment
    const commentBox = document.querySelector('.comment-box');
    if (commentBox) {
        commentBox.scrollTop = commentBox.scrollHeight;
    }
});

// LB initialization
function initializeLightbox() {
    const lightbox = document.getElementById('lightbox');
    const lightboxImg = lightbox?.querySelector('img');
    const mainPhoto = document.querySelector('.photo-section img');

    if (mainPhoto && lightbox && lightboxImg) {
        mainPhoto.addEventListener('click', function () {
            lightbox.style.display = 'flex';
            lightboxImg.src = this.src;
        });

        lightbox.addEventListener('click', function () {
            lightbox.style.display = 'none';
        });
    }
}

// WL initialization
function initializeWishlist() {
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

    // WL submission
    const wishlistForm = document.getElementById('wishlistForm');
    if (wishlistForm) {
        wishlistForm.addEventListener('submit', handleWishlistSubmit);
    }
}

// WL Submit handler
function handleWishlistSubmit(e) {
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
    // Time data is NOT INCLUDED in calculation
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
            // TODO: JWT 토큰 추가 필요
            // 'Authorization': 'Bearer ' + localStorage.getItem('token')
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
        const wishlistToggleBtn = document.getElementById('wishlistToggleBtn');
        wishlistToggleBtn.textContent = '✔️ Wished';
        wishlistToggleBtn.classList.add('wished');
        document.getElementById('wishlistPopover').style.display = 'none';
        alert('위시리스트에 추가되었습니다.');
    })
    .catch(err => {
        console.error('Error details:', err);
        alert(err.message);
    });
}

// Comments initialization
function initializeComments() {
    const commentBox = document.querySelector('.comment-box');
    if (commentBox) {
        const logId = commentBox.dataset.logId;
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
                            // TODO: JWT token
                            // 'Authorization': 'Bearer ' + localStorage.getItem('token')
                        },
                        body: JSON.stringify({
                            content: content
                        })
                    })
                    .then(res => {
                        if (!res.ok) throw new Error('Comment creation failed');
                        return res.json();
                    })
                    .then(data => {
                        const commentList = document.querySelector('.comment-box');
                        const newComment = document.createElement('div');
                        newComment.className = 'comment';
                        newComment.setAttribute('data-comment-id', data.comment_id);
                        
                        // 날짜 포맷팅 함수
                        const formatDate = (date) => {
                            const year = date.getFullYear();
                            const month = String(date.getMonth() + 1).padStart(2, '0');
                            const day = String(date.getDate()).padStart(2, '0');
                            const hours = String(date.getHours()).padStart(2, '0');
                            const minutes = String(date.getMinutes()).padStart(2, '0');
                            return `${year}.${month}.${day} ${hours}:${minutes}`;
                        };

                        newComment.innerHTML = `
                            <div class="comment-header">
                                <div class="comment-info">
                                    <strong>${data.user_nickname}</strong>
                                    <span class="comment-date">${data.updated_at || formatDate(new Date())}</span>
                                </div>
                                <div class="comment-actions">
                                    <div class="default-actions">
                                        <button class="edit-btn">수정</button>
                                        <button class="delete-btn">삭제</button>
                                    </div>
                                    <div class="edit-actions" style="display: none;">
                                        <button class="save-btn">저장</button>
                                        <button class="cancel-btn">취소</button>
                                    </div>
                                </div>
                            </div>
                            <div class="comment-body">
                                <div class="comment-content">${data.content}</div>
                                <div class="edit-form" style="display: none;">
                                    <textarea class="edit-input">${data.content}</textarea>
                                </div>
                            </div>
                        `;
                        // 새 댓글을 맨 아래에 추가
                        commentList.appendChild(newComment);
                        this.querySelector('textarea').value = '';
                        scrollToNewComment();
                    })
                    .catch(err => {
                        console.error(err);
                        alert('댓글 등록에 실패했습니다.');
                    });
                }
            });
        }

        commentBox.addEventListener('click', handleCommentActions);
    }
}

// Comment actions handler
function handleCommentActions(e) {
    const target = e.target;
    const commentElement = target.closest('.comment');
    if (!commentElement) return;

    const commentId = commentElement.dataset.commentId;
    const logId = document.querySelector('.comment-box').dataset.logId;
    const editBtn = commentElement.querySelector('.edit-btn');
    const deleteBtn = commentElement.querySelector('.delete-btn');
    const saveBtn = commentElement.querySelector('.save-btn');
    const cancelBtn = commentElement.querySelector('.cancel-btn');
    const editForm = commentElement.querySelector('.edit-form');
    const commentContent = commentElement.querySelector('.comment-content');
    const editInput = commentElement.querySelector('.edit-input');
    const editActions = commentElement.querySelector('.edit-actions');

    if (target === editBtn) {
        editForm.style.display = 'inline';
        commentContent.style.display = 'none';
        editBtn.style.display = 'none';
        deleteBtn.style.display = 'none';
        editActions.style.display = 'flex';
        commentElement.classList.add('editing');
    } else if (target === cancelBtn) {
        editForm.style.display = 'none';
        commentContent.style.display = 'inline';
        editBtn.style.display = 'inline';
        deleteBtn.style.display = 'inline';
        editActions.style.display = 'none';
        commentElement.classList.remove('editing');
    } else if (target === saveBtn) {
        const newContent = editInput.value.trim();
        if (!newContent) return;

        fetch(`/gallery/${logId}/comments/${commentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
                // TODO: JWT token
                // 'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify({ content: newContent })
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
            editActions.style.display = 'none';
            commentElement.classList.remove('editing');
        })
        .catch(err => {
            console.error(err);
            alert('댓글 수정에 실패했습니다.');
        });
    } else if (target === deleteBtn) {
        if (confirm('댓글을 삭제하시겠습니까?')) {
            fetch(`/gallery/${logId}/comments/${commentId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                    // TODO: JWT token
                    // 'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            })
            .then(res => {
                if (!res.ok) throw new Error('Delete failed');
                commentElement.remove();
            })
            .catch(err => {
                console.error(err);
                alert('댓글 삭제에 실패했습니다.');
            });
        }
    }
}

// Like functionality initialization
function initializeLikes() {
    const likeBtn = document.querySelector('.like-btn');
    if (likeBtn) {
        likeBtn.addEventListener('click', function () {
            const logId = this.dataset.logId;
            const button = this;
            const likeCount = document.querySelector('.like-count');

            fetch(`/gallery/${logId}/likes`, {
                method: 'POST',
                headers: {
                    // TODO: JWT token
                    // 'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            })
            .then(response => response.json())
            .then(data => {
                button.textContent = data.liked ? '💔Unlike' : '❤️Like';
                button.classList.toggle('liked', data.liked);
                likeCount.textContent = '❤️ ' + data.likeCount + ' Likes';
            })
            .catch(error => {
                console.error('Error:', error);
                button.textContent = button.classList.contains('liked') ? '💔Unlike' : '❤️Like';
            });
        });
    }
}

// Scroll to new comment
function scrollToNewComment() {
    const commentBox = document.querySelector('.comment-box');
    if (commentBox) {
        commentBox.scrollTo({
            top: commentBox.scrollHeight,
            behavior: 'smooth'
        });
    }
}