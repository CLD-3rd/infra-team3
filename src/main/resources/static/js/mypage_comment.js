document.addEventListener("DOMContentLoaded", async function () {
  const commentBody = document.getElementById("commentTableBody");

  try {
    const response = await fetch("/api/mypage/comments");
    const comments = await response.json();

    if (Array.isArray(comments)) {
      commentBody.innerHTML = "";

      comments.forEach(comment => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${comment.created_at}</td>
          <td>여행지 없음</td>
          <td><a href="#">${comment.content}</a></td>
        `;
        commentBody.appendChild(tr);
      });
    } else {
      commentBody.innerHTML = "<tr><td colspan='3'>댓글을 불러오지 못했습니다.</td></tr>";
    }

  } catch (error) {
    console.error("댓글 불러오기 실패:", error);
    commentBody.innerHTML = "<tr><td colspan='3'>에러 발생</td></tr>";
  }
});
