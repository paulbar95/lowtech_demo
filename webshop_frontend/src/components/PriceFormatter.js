export const formatter = new Intl.NumberFormat(navigator.language, {
    style: 'currency',
    currency: 'EUR',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
});