document.addEventListener("DOMContentLoaded", () => {
  const tooltip = document.getElementById('tooltip');
  const countries = document.querySelectorAll('.country');

  document.addEventListener('click', (e) => {
    if (e.target.classList.contains('country')) {
      const code = e.target.dataset.id;
      const template = document.getElementById('template-' + code);

      if (template) {
        tooltip.innerHTML = template.innerHTML;
        tooltip.style.left = (e.pageX + 15) + 'px';
        tooltip.style.top = (e.pageY + 15) + 'px';
        tooltip.style.opacity = 1;
      }
    } else if (!tooltip.contains(e.target)) {
      tooltip.style.opacity = 0;
    }
  });
});
