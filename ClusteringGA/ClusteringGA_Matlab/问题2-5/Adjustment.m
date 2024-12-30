function [chrom1,length1] = Adjustment(chrom,length)
for i = 2:1:length-1
    if(ismember(chrom(i),chrom(1:i-1))||ismember(chrom(i),chrom(i+1:end)))
        break;
    end
end
samecode = chrom(i);
index = find(chrom == samecode);
chrom(index(1):index(2)-1) = [];
chrom1 = chrom;
length1 = size(chrom,2);