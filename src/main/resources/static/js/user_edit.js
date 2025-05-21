document.addEventListener("DOMContentLoaded", function () {
  const editForm = document.getElementById("editForm");
  const resultMessage = document.getElementById("resultMessage");

  editForm.addEventListener("submit", async function (e) {
    e.preventDefault();

    const nickname = document.getElementById("nickname").value.trim();
    const password = document.getElementById("password").value.trim();

    try {
      const response = await fetch("/api/mypage/edit", {
        method: "PATCH",
		credentials: "same-origin",  // 
        headers: {
          "Content-Type": "application/json"
          // "Authorization": `Bearer ${localStorage.getItem("token")}` // 추후 JWT 인증 활성화
        },
        body: JSON.stringify({ nickname, password })
      });

      const data = await response.json();

      if (response.ok) {
        resultMessage.innerHTML = `<div class="alert alert-success">회원정보가 성공적으로 수정되었습니다.</div>`;
        setTimeout(() => {
          const modal = bootstrap.Modal.getInstance(document.getElementById("editUserModal"));
          modal.hide();
          window.location.reload();
        }, 1000);
      } else {
        // 에러 메시지에 따라 분기 처리
        let message = "수정에 실패했습니다.";
        if (data.message) {
          if (data.message.includes("비밀번호")) {
            message = "비밀번호가 일치하지 않습니다.";
          } else if (data.message.includes("중복")) {
            message = "이미 사용 중인 닉네임입니다.";
          } else if (data.message.includes("동일")) {
            message = "기존 닉네임과 동일합니다.";
          } else {
            message = data.message;
          }
        }

        resultMessage.innerHTML = `<div class="alert alert-danger">${message}</div>`;
      }

    } catch (error) {
      resultMessage.innerHTML = `<div class="alert alert-danger">에러가 발생했습니다: ${error.message}</div>`;
    }
  });
});
