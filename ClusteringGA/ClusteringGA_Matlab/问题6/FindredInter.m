%�ҵ�·�����Ǻ�ɫ�����·��
function RedInter = FindredInter(InterNum,Red_areas)
RedInter = [];
for i = 1:1:size(InterNum,1)
    if ismember(InterNum(i,2:3),Red_areas,'rows')
        RedInter = [RedInter,InterNum(i,1)];
    end
end
