console.log("Travel log scripts loaded")
document.addEventListener('DOMContentLoaded', function () {
    const toggleBtn = document.getElementById('wishlistToggleBtn');
    const popover = document.getElementById('wishlistPopover');

    if (toggleBtn && popover) {
        toggleBtn.addEventListener('click', function (event) {
            event.preventDefault();
            popover.style.display = (popover.style.display === 'none' || popover.style.display === '') ? 'block' : 'none';
        });
    }

    document.addEventListener('click', function (e) {
        const container = document.querySelector('.wishlist-popover-container');
        if (!container.contains(e.target)) {
            popover.style.display = 'none';
        }
    });
});
document.addEventListener('DOMContentLoaded', function () {
    const toggleBtn = document.getElementById('wishlistToggleBtn');
    const popover = document.getElementById('wishlistPopover');

    if (toggleBtn && popover) {
        toggleBtn.addEventListener('click', function (event) {
            event.preventDefault();
            popover.style.display = (popover.style.display === 'none' || popover.style.display === '') ? 'block' : 'none';
        });
    }

    document.addEventListener('click', function (e) {
        const container = document.querySelector('.wishlist-popover-container');
        if (!container.contains(e.target)) {
            popover.style.display = 'none';
        }
    });

    // Comment editing logic moved here
    const commentBox = document.querySelector('.comment-box');
    if (commentBox) {
        const logId = commentBox.dataset.logId;
        const token = localStorage.getItem('token');

        function refreshCommentContent(p, newContent) {
            p.querySelector('.comment-content').textContent = newContent;
            p.querySelector('.comment-content').style.display = 'inline';
            p.querySelector('.edit-btn').style.display = 'inline';
            p.querySelector('.edit-form').style.display = 'none';
        }

        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                const p = btn.closest('p');
                p.querySelector('.comment-content').style.display = 'none';
                btn.style.display = 'none';
                p.querySelector('.delete-btn').style.display = 'none';
                p.querySelector('.edit-form').style.display = 'inline';
            });
        });
        document.querySelectorAll('.cancel-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                const p = btn.closest('p');
                p.querySelector('.comment-content').style.display = 'inline';
                p.querySelector('.edit-btn').style.display = 'inline';
                p.querySelector('.delete-btn').style.display = 'inline';
                p.querySelector('.edit-form').style.display = 'none';
            });
        });
        document.querySelectorAll('.save-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                const p = btn.closest('p');
                const commentId = p.dataset.commentId;
                const input = p.querySelector('.edit-input');
                const newContent = input.value.trim();
                if (newContent) {
                    fetch(`/gallery/${logId}/comments/${commentId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + token
                        },
                        body: JSON.stringify({content: newContent})
                    })
                        .then(res => {
                            if (!res.ok) throw new Error('Update failed');
                            return res.json();
                        })
                        .then(data => {
                            refreshCommentContent(p, data.content);
                        })
                        .catch(err => console.error(err));
                }
            });
        });
        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                const p = btn.closest('p');
                const commentId = p.dataset.commentId;
                if (confirm('삭제하시겠습니까>?')) {
                    fetch(`/gallery/${logId}/comments/${commentId}`, {
                        method: 'DELETE',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    })
                        .then(res => {
                            if (!res.ok) throw new Error('delete faiedl');
                            p.remove();
                        })
                        .catch(err => console.error(err));
                }
            });
        });
    }
});